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
import androidx.compose.runtime.Composable
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

@Composable
fun CountBy(speak: (String) -> Unit) {
    var countBy by rememberSaveable { mutableStateOf(1.toBigInteger()) }
    LazyColumn {
        item {
            TextField(
                value = if (countBy == 0.toBigInteger()) {
                    0.toString()
                } else {
                    countBy.toString()
                },
                onValueChange = {
                    if (it.isNotBlank()) {
                        countBy = it.toBigDecimal().toBigInteger()
                    } else {
                        countBy = 0.toBigInteger()
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Enter Number") }
            )
        }
        item {
            Slider(value = countBy.toFloat(),
                valueRange = 0f..1_000_000f,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    countBy = it.toLong().toBigInteger()
                }
            )
        }
        for (i in 0..1000) {
            val num: BigInteger = i.toBigInteger() * countBy
            val text = "%,d".format(num)
            item {
                Text(
                    text = text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { speak(text) }
                        .padding(16.dp),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )

                Divider(color = Color.Black, thickness = 1.dp)
            }
        }
    }
}