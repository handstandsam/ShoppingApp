package com.handstandsam.shoppingapp.features.countby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.graph
import com.handstandsam.shoppingapp.utils.TextToSpeechEngine

class CountByActivity : ComponentActivity() {

    private val graph: AppGraph get() = application.graph()

    private val textToSpeechEngine by lazy { TextToSpeechEngine(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountBy { textToSpeechEngine.speak(it) }
        }

    }
}