package dev.saied.flowbindings.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.saied.flowbindings.flowbindings.view.clicks
import dev.saied.flowbindings.flowbindings.view.longClicks
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var clickCount = 0
        button.clicks()
            .onEach {
                Toast.makeText(this@MainActivity, "${++clickCount} clicks!", Toast.LENGTH_SHORT).show()
            }
            .launchIn(lifecycleScope)

        var longClickCount = 0
        button.longClicks()
            .onEach {
                Toast.makeText(this@MainActivity, "${++longClickCount} Long clicks!", Toast.LENGTH_SHORT).show()
            }
            .launchIn(lifecycleScope)


    }
}