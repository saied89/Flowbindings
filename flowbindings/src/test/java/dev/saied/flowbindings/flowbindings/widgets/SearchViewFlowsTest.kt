package dev.saied.flowbindings.flowbindings.widgets

import android.widget.SearchView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewFlowsTest {

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Test
    fun queryTextChanges() {
        runBlockingTest {
            val context = InstrumentationRegistry.getInstrumentation().context
            val searchView = SearchView(context)
            val collectJob = Job()
            val scope = CoroutineScope(coroutineContext + collectJob)
            searchView.setQuery("Initial", false)
            val queryChannel = searchView.queryTextChanges()
                .produceIn(scope)
            assertEquals("Initial", queryChannel.receive().toString())
            searchView.setQuery("H", false)
            assertEquals("H", queryChannel.receive())
            searchView.setQuery("He", false)
            assertEquals("He", queryChannel.receive())

            collectJob.cancelAndJoin()

            searchView.setQuery("Silent", false)
            assert(queryChannel.isClosedForReceive)
        }
    }

}