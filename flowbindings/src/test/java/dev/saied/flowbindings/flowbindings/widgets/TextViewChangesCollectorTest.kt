package dev.saied.flowbindings.flowbindings.widgets

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TextViewChangesCollectorTest {
    @Test
    fun textChanges() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().context
            val textView = TextView(context)
            val recordList = mutableListOf<CharSequence>()
            val collectJob = launch {
                textView.textChanges()
                    .collect {
                        recordList.add(it)
                    }
            } // subscribes to clicks and then suspends to listen to clicks
            delay(500) // delay so that subscription happens in the coroutine.
            val strings = listOf("a", "b", "xajlk", "saied")
            strings.forEach {
                textView.text = it
            }
            collectJob.cancelAndJoin() // cancel the subscription and wait for it to end
            assertEquals(strings.size, recordList.size)
            strings.mapIndexed { index, s ->
                assertEquals(s, recordList[index].toString())
            }
        }
    }
}