package dev.saied.flowbindings.flowbindings.view

import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ViewClickCollectorTest {

    @InternalCoroutinesApi
    @Test
    fun clicks() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().context
            val view = View(context)
            val recordList = mutableListOf<Unit>()
            val collectJob = launch {
                view.clicks()
                    .collect {
                        recordList.add(it)
                    }
            } // subscribes to clicks and then suspends to listen to clicks
            delay(500) // delay so that subscription happens in the coroutine.
            repeat(5) {
                view.performClick()
            }
            collectJob.cancelAndJoin() // cancel the subscription and wait for it to end
            Assert.assertEquals(5, recordList.size)
            assert(recordList.all { it == Unit })
        }
    }
}