package dev.saied.flowbindings.flowbindings.widgets

import android.widget.RadioGroup
import dev.saied.flowbindings.flowbindings.initValueChannelFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
fun RadioGroup.checkedChanges(): Flow<Int?> = initValueChannelFlow(checkedRadioButtonId) {
    var lastChecked: Int = checkedRadioButtonId

    setOnCheckedChangeListener { _, checkedId ->
        if (checkedId != lastChecked) {
            lastChecked = checkedId
            offer(lastChecked)
        }
    }

    awaitClose { setOnCheckedChangeListener(null) }
}