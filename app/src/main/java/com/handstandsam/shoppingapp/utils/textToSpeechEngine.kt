package com.handstandsam.shoppingapp.utils

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.Locale

class TextToSpeechEngine(context: Context) {

    private val applicationContext = context.applicationContext

    private val textToSpeechEngine: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(applicationContext) { status ->
            // set our locale only if init was success.
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale.US
            }
        }
    }


    fun speak(text: String) {
        // Check if user hasn't input any text.
        if (text.isNotEmpty()) {
            textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
        } else {
            Toast.makeText(applicationContext, "Text cannot be empty", Toast.LENGTH_LONG).show()
        }
    }
}