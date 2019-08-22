package dev.saied.flowbindings.flowbindings.widgets

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.saied.flowbindings.flowbindings.view.longClicks
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
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

    @ExperimentalCoroutinesApi
    @Test
    fun checkedChanges() =
        runBlocking {
            val recordList = mutableListOf<Int>()
            val collectJob = launch {
                view.checkedChanges()
                    .collect {
                        recordList.add(it)
                    }
            }
            delay(500)
            view.check(1)
            view.check(2)
            view.clearCheck()

            collectJob.cancelAndJoin()
            assertEquals(3, recordList.size)
            assertEquals(1, recordList[0])
            assertEquals(2, recordList[1])
            assertEquals(-1, recordList[2])
        }
}