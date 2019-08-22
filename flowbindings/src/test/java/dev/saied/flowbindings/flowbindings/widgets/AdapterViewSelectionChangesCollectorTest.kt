package dev.saied.flowbindings.flowbindings.widgets

import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AdapterViewSelectionChangesCollectorTest {

    @Test
    fun itemSelections() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val spinner = Spinner(context)
        spinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, (1..10).map { it.toString() })

        runBlocking {
            val recordList = mutableListOf<Int>()
            val collectJob = launch {
                spinner.itemSelections().collect {
                    recordList.add(it)
                }
            }
            delay(500)
            val selection = listOf(1, 5, 3, 7)
            selection.forEach {
                spinner.setSelection(it)
            }
            collectJob.cancelAndJoin()
            assertArrayEquals(selection.toIntArray(), recordList.toIntArray())
        }
    }
}