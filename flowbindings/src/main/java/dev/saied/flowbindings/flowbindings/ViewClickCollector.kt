package dev.saied.flowbindings.flowbindings

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
suspend fun View.clicks() = channelFlow {
    setOnClickListener {
        offer(Unit)
    }
    awaitClose {
        setOnClickListener(null)
    }
}

