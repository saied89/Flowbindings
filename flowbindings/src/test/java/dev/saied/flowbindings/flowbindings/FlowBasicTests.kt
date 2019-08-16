package dev.saied.flowbindings.flowbindings

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowBasicTests {
    @InternalCoroutinesApi
    @Test
    fun flowItemsAreRecieved() {
        val flow = flowOf(1, 2, 3)
        runBlocking {
            flow.collect(object : FlowCollector<Int> {
                override suspend fun emit(value: Int) {
                    println(value)
                }
            })
        }

    }
}