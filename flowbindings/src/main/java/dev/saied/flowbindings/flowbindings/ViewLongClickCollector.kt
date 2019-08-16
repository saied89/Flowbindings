package dev.saied.flowbindings.flowbindings

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
fun View.longClicks() = channelFlow {
    setOnLongClickListener {
        offer(Unit)
    }

    awaitClose { setOnLongClickListener(null) }
}