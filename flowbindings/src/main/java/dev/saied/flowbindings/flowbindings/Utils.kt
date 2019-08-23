package dev.saied.flowbindings.flowbindings

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.channelFlow

/**
 * Offers an [initValue] before executing the production [block].
 *
 * @param initValue the initial value to emit before running the production [block]
 * @return a Flow of type [T] which recieves the initial value and then the emissions
 * of the production [block].
 */
@ExperimentalCoroutinesApi
fun <T> initValueChannelFlow(initValue: T, block: suspend ProducerScope<T>.() -> Unit) =
    channelFlow {
        offer(initValue)
        block.invoke(this)
    }