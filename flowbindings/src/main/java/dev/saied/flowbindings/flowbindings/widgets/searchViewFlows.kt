package dev.saied.flowbindings.flowbindings.widgets

import android.widget.SearchView
import dev.saied.flowbindings.flowbindings.initValueChannelFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow

/**
 * Emits when the search view query text is changed.
 * @return a [Flow] of type [String] that will receive query text changes.
 *
 * *Note:* A value will be emitted immediately on subscribe.
 */
@ExperimentalCoroutinesApi
fun SearchView.queryTextChanges(): Flow<CharSequence> = initValueChannelFlow(query) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean = false

        override fun onQueryTextChange(newText: String): Boolean {
            offer(newText)
            return true
        }
    })

    awaitClose { setOnQueryTextListener(null) }
}