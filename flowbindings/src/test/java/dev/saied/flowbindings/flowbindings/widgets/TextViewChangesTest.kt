package dev.saied.flowbindings.flowbindings.widgets

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.produceIn
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TextViewChangesTest {
    @FlowPreview
    @Test
    fun textChanges() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().context
            val textView = TextView(context)
            textView.text = "Initial"
            val collectJob = Job()
            val collectChannel = textView.textChanges().produceIn(CoroutineScope(coroutineContext + collectJob))
             // subscribes to clicks and then suspends to listen to clicks
            delay(500) // delay so that subscription happens in the coroutine.
            assertEquals("Initial", collectChannel.receive().toString())

            val strings = listOf("a", "b", "xajlk", "saied")
            strings.forEach {
                textView.text = it
                assertEquals(it, collectChannel.receive().toString())
            }

            collectJob.cancelAndJoin() // cancel the subscription and wait for it to end
            assert(collectChannel.isClosedForReceive)
        }
    }
}