package com.handstandsam.shoppingapp.features.poke

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.R
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
            val configuration = LocalConfiguration.current
            val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
            val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }


            val pokeballSizeDp by remember { mutableStateOf(150.dp) }
            val pokeballSizePx =
                with(LocalDensity.current) {
                    Size(
                        width = pokeballSizeDp.toPx(),
                        height = pokeballSizeDp.toPx()
                    )
                }
            val originalOffset = Offset(
                x = (screenWidth / 2) - (pokeballSizePx.width / 2),
//                y = screenHeight - pokeballSizePx.height - 200,
                y = (screenHeight / 2) - (pokeballSizePx.height / 2),
            )

            var isDragging by remember { mutableStateOf(false) }
            var currentOffset by remember { mutableStateOf(originalOffset) }

            var animationSpec = spring<Float>(dampingRatio = Spring.DampingRatioMediumBouncy)
            val offsetAnimationX: Float by animateFloatAsState(
                currentOffset.x,
                animationSpec,
            )

            val offsetAnimationY: Float by animateFloatAsState(
                currentOffset.y,
                animationSpec,
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                println("$change $dragAmount")
                                change.consumeAllChanges()
                                val x = (currentOffset.x + dragAmount.x)
                                    .coerceIn(0f, size.width.toFloat() - pokeballSizePx.width)
                                val y = (currentOffset.y + dragAmount.y)
                                    .coerceIn(0f, size.height.toFloat() - pokeballSizePx.height)
                                currentOffset = Offset(x, y)
                            },
                            onDragStart = {
                                isDragging = true
                            },
                            onDragEnd = {
//                                isDragging = false
                                currentOffset = originalOffset
                            }
                        )
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.pokecatchbackground),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "content description"
                )

                with(LocalDensity.current) {
                    Size(
                        width = pokeballSizeDp.toPx(),
                        height = pokeballSizeDp.toPx()
                    )
                }
                val offset =
//                    if (isDragging) {
//                        Pair(currentOffset.x, currentOffset.y)
//                    } else {
                    Pair(offsetAnimationX, offsetAnimationY)
//                    }
                with(LocalDensity.current) {
                    Pokeball(
                        sizedp = pokeballSizeDp,
                        modifier = Modifier
                            .offset(
//                            x = currentOffset.x.dp,
//                            y = currentOffset.y.dp,
                                x = offset.first.toDp(),
                                y = offset.second.toDp(),
                            )
                    )
                }
            }
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