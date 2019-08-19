package dev.saied.flowbindings.flowbindings.widgets

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
fun TextView.textChanges() = channelFlow {
    val textChangeWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            offer(p0)
        }

        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
    addTextChangedListener(textChangeWatcher)

    awaitClose { removeTextChangedListener(textChangeWatcher) }
}