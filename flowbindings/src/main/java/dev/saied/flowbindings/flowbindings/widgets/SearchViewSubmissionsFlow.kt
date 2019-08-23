package dev.saied.flowbindings.flowbindings.widgets

import android.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

/**
 * Emits when user submits the query.
 * @return a [Flow] of type [String] that will receive query submissions.
 */
@ExperimentalCoroutinesApi
fun SearchView.queryTextSubmits() = channelFlow {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            offer(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean = false
    })

    awaitClose { setOnQueryTextListener(null) }
}