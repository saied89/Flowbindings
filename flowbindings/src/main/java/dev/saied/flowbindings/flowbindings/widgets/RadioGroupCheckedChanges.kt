package dev.saied.flowbindings.flowbindings.widgets

import android.widget.RadioGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
fun RadioGroup.checkedChanges() = channelFlow {
    var lastChecked = -1
    setOnCheckedChangeListener { group, checkedId ->
        if (checkedId != lastChecked) {
            lastChecked = checkedId
            offer(lastChecked)
        }
    }

    awaitClose { setOnCheckedChangeListener(null) }
}