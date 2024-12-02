import org.example.MichaelScottQueue
import org.jetbrains.kotlinx.lincheck.annotations.*
import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import org.junit.jupiter.api.Test
import java.util.Optional

class MichaelScottQueueLincheckTest {
    private val queue = MichaelScottQueue<Int>()
    @Operation
    fun symmetricAddAndRemoveTest() {
        queue.add(1)
        queue.add(2)
        queue.add(3)

        // Удаляем элементы и проверяем их порядок
        assert(queue.remove().get() == 1)
        assert(queue.remove().get() == 2)
        assert(queue.remove().get() == 3)

        // Пытаемся удалить из пустой очереди
        assert(queue.remove().isEmpty)
    }
    @Test
    fun stressTest() = StressOptions()
        .iterations(100)
        .threads(1)
        .check(this::class)
}
