package com.handstandsam.shoppingapp.features.poke

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.graph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

class PokeActivity : ComponentActivity() {

    private val graph: AppGraph get() = application.graph()

    private val textToSpeechEngine: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(this,
            TextToSpeech.OnInitListener { status ->
                // set our locale only if init was success.
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.US
                }
            })
    }

    fun speak(text: String) {
        // Check if user hasn't input any text.
        if (text.isNotEmpty()) {
            // Lollipop and above requires an additional ID to be passed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Call Lollipop+ function
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
            } else {
                // Call Legacy function
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }
        } else {
            Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryViewModel = ViewModelProvider(this, graph.viewModelFactory)
            .get(PokeViewModel::class.java)


        setContent {
            Pokeball ()
        }

        lifecycleScope.launchWhenCreated {
            categoryViewModel.sideEffects
                .onEach {
                    when (it) {
                        is PokeViewModel.SideEffect.LaunchItemDetailActivity -> {
                            ItemDetailActivity.launch(this@PokeActivity, it.item)
                        }
                    }
                }
                .launchIn(this)
        }

    }

    companion object {
    }
}