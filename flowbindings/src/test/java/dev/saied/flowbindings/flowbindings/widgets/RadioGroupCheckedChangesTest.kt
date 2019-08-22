package dev.saied.flowbindings.flowbindings.widgets

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.produceIn
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RadioGroupCheckedChangesTest {
    val context = InstrumentationRegistry.getInstrumentation().context
    private val view = RadioGroup(context)

    @Before
    fun setup() {
        val button1 = RadioButton(context)
        button1.id = 1
        view.addView(button1)
        val button2 = RadioButton(context)
        button2.id = 2
        view.addView(button2)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Test
    fun checkedChanges() =
        runBlocking {
            val collectJob = Job()

            val recordChannel = view.checkedChanges()
                .produceIn(CoroutineScope(coroutineContext + collectJob))

            delay(500)
            view.check(1)
            assertEquals(1, recordChannel.receive())
            view.check(2)
            assertEquals(2, recordChannel.receive())
            view.clearCheck()
            assertEquals(-1, recordChannel.receive())

            collectJob.cancelAndJoin()
            assert(recordChannel.isClosedForReceive)
        }
}