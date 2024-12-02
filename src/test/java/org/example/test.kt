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
    fun add(value: Int) {
        queue.add(value)
    }

    @Operation
    fun remove(): Optional<Int> {
        return queue.remove()
    }
    @Test
    fun stressTest() = StressOptions()
        .check(this::class)
}
