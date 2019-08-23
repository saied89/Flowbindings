package dev.saied.flowbindings.flowbindings.widgets

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.produceIn
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AdapterViewSelectionChangesTest {

    @FlowPreview
    @Test
    fun itemSelections() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val spinner = Spinner(context)
        spinner.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            (1..10).map { it.toString() }
        )

        runBlocking {
            val collectJob = Job()
            val collectChannel = spinner.itemSelections()
                .produceIn(CoroutineScope(coroutineContext + collectJob))
            delay(500)
            assertEquals(0, collectChannel.receive())
            val selection = listOf(1, 5, 3, 7)
            selection.forEach {
                spinner.setSelection(it)
                assertEquals(it, collectChannel.receive())
            }
            collectJob.cancelAndJoin()
            assert(collectChannel.isClosedForReceive)
        }
    }
}