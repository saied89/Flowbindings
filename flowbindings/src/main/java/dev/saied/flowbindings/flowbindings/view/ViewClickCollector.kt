package dev.saied.flowbindings.flowbindings.view

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
fun View.clicks() = channelFlow {
    setOnClickListener {
        offer(Unit)
    }
    awaitClose {
        setOnClickListener(null)
    }
}

