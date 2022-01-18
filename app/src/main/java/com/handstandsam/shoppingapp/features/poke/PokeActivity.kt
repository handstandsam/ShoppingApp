package com.handstandsam.shoppingapp.features.poke

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.graph
import com.handstandsam.shoppingapp.utils.TextToSpeechEngine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.random.Random


class PokeActivity : ComponentActivity() {

    private val graph: AppGraph get() = application.graph()

    private val textToSpeechEngine by lazy { TextToSpeechEngine(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeechEngine // Initialize
        val mp: MediaPlayer = MediaPlayer.create(this@PokeActivity, R.raw.ding)

        val categoryViewModel = ViewModelProvider(this, graph.viewModelFactory)
            .get(PokeViewModel::class.java)

        setContent {
            val configuration = LocalConfiguration.current
            val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
            val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
            val pokeballAnimationDuration = 1200
            val pokeballSizeDp by remember { mutableStateOf(150.dp) }

            var ballThrown by remember { mutableStateOf(false) }
            val pokeballSizePx =
                with(LocalDensity.current) {
                    Size(
                        width = pokeballSizeDp.toPx(),
                        height = pokeballSizeDp.toPx()
                    )
                }
            val startPositionOffset = Offset(
                x = (screenWidth / 2) - (pokeballSizePx.width / 2),
                y = screenHeight - (pokeballSizePx.height * 1.5).toFloat(),
            )
            val catchPositionOffset = Offset(
                x = (screenWidth / 2) - (pokeballSizePx.width / 2),
                y = with(LocalDensity.current) { 350.dp.toPx() },
            )

            var currentOffset by remember { mutableStateOf(startPositionOffset) }
            var currentScale by remember { mutableStateOf(1f) }

            var isDraggingPokeball by remember { mutableStateOf(false) }
            val pokeballOffsetAnimation by animateOffsetAsState(
                targetValue = currentOffset,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            )

            var pokemonImageScale by remember { mutableStateOf(1f) }

            val pokemonAnimatedImageScale: Float by animateFloatAsState(
                targetValue = pokemonImageScale,
                animationSpec = tween(250, easing = FastOutSlowInEasing),
            )

            val pokeballScaleAnimationValue: Float by animateFloatAsState(
                targetValue = currentScale,
                animationSpec = tween(pokeballAnimationDuration, easing = FastOutSlowInEasing),
            )

            var pokemonId: Int by remember { mutableStateOf(1) }
            var pokemonImageUrl: String by remember {
                mutableStateOf(
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
                )
            }

            fun pokemonName(): String {
                return PokemonNames[pokemonId - 1]
            }

            fun newImage(reason: String) {
                val playSound = false
                if (playSound) {
                    mp.seekTo(0)
                    mp.start()
                }
                pokemonId = Random.nextInt(1, 600)
                println("Random because $reason: $pokemonId")
                pokemonImageUrl =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"

                mp.setOnCompletionListener {
                    textToSpeechEngine.speak("A ${pokemonName()} Appeared!")
                }
                fun reset() {
                    ballThrown = false
                    isDraggingPokeball=true
                    pokemonImageScale = 1f
                    currentOffset = startPositionOffset
                }
                reset()
            }

            var doInfiniteRotate by remember { mutableStateOf(false) }

            val keyframeAnimation by animateOffsetAsState(
                targetValue = currentOffset,
                animationSpec = keyframes {
                    durationMillis = pokeballAnimationDuration
//                    Offset(currentOffset.x, currentOffset.y * 0f) at 500 with LinearOutSlowInEasing // ms
                    Offset(currentOffset.x, currentOffset.y * 0.30f) at 600 with FastOutLinearInEasing
//                    Offset(currentOffset.x, currentOffset.y * 0f) at 1500 with LinearEasing // ms
                },
                finishedListener = {
                    if (ballThrown) {
                        pokemonImageScale = 0f
                    }
                    doInfiniteRotate = false
//                    newImage("onDragEnd")
                }
            )
            val finalAnimation =
                if (isDraggingPokeball) {
                    pokeballOffsetAnimation
                } else {
                    keyframeAnimation
                }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                if (isDraggingPokeball) {
                                    change.consumeAllChanges()
                                    val newX = (currentOffset.x + dragAmount.x)
                                        .coerceIn(0f, size.width.toFloat() - pokeballSizePx.width)
                                    val newY = (currentOffset.y + dragAmount.y)
                                        .coerceIn(0f, size.height.toFloat() - pokeballSizePx.height)
                                    currentOffset = Offset(newX, newY)
                                }
                            },
                            onDragStart = {
                                println("Drag Start")
                                isDraggingPokeball = it.isTouchWithinBounds(currentOffset, pokeballSizePx)
                            },
                            onDragEnd = {
                                ballThrown = true
                                currentOffset = if (ballThrown) {
                                    catchPositionOffset
                                } else {
                                    startPositionOffset
                                }
                                currentScale = if (ballThrown) {
                                    .50f
                                } else {
                                    1f
                                }
                                isDraggingPokeball = false
                                doInfiniteRotate = true
                            }
                        )
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.pokecatchbackground),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "content description"
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(100.dp)
                    )
                    Text(
                        text = pokemonName(),
                        style = MaterialTheme.typography.h4.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable { newImage("clicked text") }
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(150.dp))
                    Image(
                        painter = rememberImagePainter(pokemonImageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.CenterHorizontally)
                            .scale(pokemonAnimatedImageScale)
                    )
                }


                val infiniteTransition = rememberInfiniteTransition()
                val angle by infiniteTransition.animateFloat(
                    initialValue = 0F,
                    targetValue = 360F,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = pokeballAnimationDuration,
                            easing = LinearEasing
                        )
                    )
                )

                with(LocalDensity.current) {
                    Pokeball(
                        sizedp = pokeballSizeDp,
                        modifier = Modifier
                            .offset(
                                x = finalAnimation.x.toDp(),
                                y = finalAnimation.y.toDp(),
                            )
                            .clickable { doInfiniteRotate = !doInfiniteRotate }
                            .scale(pokeballScaleAnimationValue)
                            .rotate(
                                if (doInfiniteRotate) {
                                    angle
                                } else {
                                    0f
                                }
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

fun Offset.isTouchWithinBounds(currentOffset: Offset, currentSizePx: Size): Boolean {
    if ((x >= currentOffset.x) && x <= (currentOffset.x + currentSizePx.width)) {
        // Within X
        if ((y >= currentOffset.y) && y <= (currentOffset.y + currentSizePx.height)) {
            // Within Y
            return true
        }
    }
    return false
}