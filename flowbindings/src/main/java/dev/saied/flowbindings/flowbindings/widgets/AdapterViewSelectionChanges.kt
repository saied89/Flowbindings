package dev.saied.flowbindings.flowbindings.widgets

import android.view.View
import android.widget.AdapterView
import dev.saied.flowbindings.flowbindings.initValueChannelFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
fun <T : android.widget.Adapter> AdapterView<T>.itemSelections() = initValueChannelFlow(selectedItemPosition) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            offer(AdapterView.INVALID_POSITION)
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            offer(p2)
        }
    }

    awaitClose { onItemSelectedListener = null }
}