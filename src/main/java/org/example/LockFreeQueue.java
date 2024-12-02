package org.example;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {
    private static class Entry<T> {
        final AtomicReference<T> data;
        final AtomicReference<Entry<T>> next;

        Entry() {
            this.data = new AtomicReference<>();
            this.next = new AtomicReference<>();
        }

        Entry(T data) {
            this.data = new AtomicReference<>(data);
            this.next = new AtomicReference<>();
        }
    }

    private final AtomicReference<Entry<T>> front;
    private final AtomicReference<Entry<T>> rear;

    public LockFreeQueue() {
        Entry<T> placeholder = new Entry<>();
        this.front = new AtomicReference<>(placeholder);
        this.rear = new AtomicReference<>(placeholder);
    }

    public void add(T item) {
        Objects.requireNonNull(item, "Item must not be null");
        Entry<T> newEntry = new Entry<>(item);
        while (true) {
            Entry<T> currentRear = rear.get();
            if (currentRear.next.compareAndSet(null, newEntry)) {
                rear.compareAndSet(currentRear, newEntry);
                return;
            } else {
                rear.compareAndSet(currentRear, currentRear.next.get());
            }
        }
    }

    public Optional<T> remove() {
        while (true) {
            Entry<T> currentFront = front.get();
            Entry<T> currentRear = rear.get();
            Entry<T> nextFront = currentFront.next.get();

            if (currentFront == currentRear) {
                if (nextFront == null) {
                    return Optional.empty();
                } else {
                    rear.compareAndSet(currentRear, nextFront);
                }
            }

            if (nextFront != null) {
                T value = nextFront.data.get();
                if (value != null && nextFront.data.compareAndSet(value, null)) {
                    if (front.compareAndSet(currentFront, nextFront)) {
                        currentFront.next.set(currentFront);
                    }
                    return Optional.of(value);
                } else {
                    if (front.compareAndSet(currentFront, nextFront)) {
                        currentFront.next.set(currentFront);
                    }
                }
            }
        }
    }
}
