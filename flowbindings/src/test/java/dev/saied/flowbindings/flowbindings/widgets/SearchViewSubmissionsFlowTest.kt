package dev.saied.flowbindings.flowbindings.widgets

import android.widget.RadioGroup
import android.widget.SearchView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewSubmissionsFlowTest {

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Test
    fun submissions() {
        runBlockingTest {
            val context = InstrumentationRegistry.getInstrumentation().context
            val searchView = SearchView(context)
            val collectJob = Job()
            val scope = CoroutineScope(coroutineContext + collectJob)
            val queryChannel = searchView.queryTextSubmits()
                .produceIn(scope)
            searchView.setQuery("H", true)
            assertEquals("H", queryChannel.receive())
            searchView.setQuery("He", true)
            assertEquals("He", queryChannel.receive())

            collectJob.cancelAndJoin()

            searchView.setQuery("Silent", true)
            assert(queryChannel.isClosedForReceive)
        }
    }
}