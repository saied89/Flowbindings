package dev.saied.flowbindings.flowbindings

import android.view.View
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class ViewClickCollectorTest {
//
//    @InternalCoroutinesApi
//    @Test
//    @UiThreadTest
//    fun clicks() {
//        runBlocking {
//            val context = InstrumentationRegistry.getInstrumentation().context
//            val view = View(context)
//            val recordList = mutableListOf<Unit>()
//            val collectJob = launch {
//                view.clicks()
//                    .collect {
//                        recordList.add(it)
//                    }
//            }
//            delay(500)
//            (1..5).forEach {
//                view.performClick()
//            }
//            collectJob.cancelAndJoin()
//            assertEquals(5, recordList.size)
//            assert(recordList.all { it == Unit })
//        }
//    }
//}