package com.handstandsam.shoppingapp.features.countby

import android.icu.util.UniversalTimeScale.toBigDecimal
import android.icu.util.UniversalTimeScale.toLong
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.graph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigInteger
import java.util.Locale

class CountByActivity : ComponentActivity() {

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
            .get(CountByViewModel::class.java)


        setContent {
            CountBy { speak(it) }
        }

        lifecycleScope.launchWhenCreated {
            categoryViewModel.sideEffects
                .onEach {
                    when (it) {
                        is CountByViewModel.SideEffect.LaunchItemDetailActivity -> {
                            ItemDetailActivity.launch(this@CountByActivity, it.item)
                        }
                    }
                }
                .launchIn(this)
        }

    }

    companion object {
    }
}