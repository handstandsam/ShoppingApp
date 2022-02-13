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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.features.poke.PokeBallStateModel.Companion.INITIAL_POKEBALL_SIZE_DP
import com.handstandsam.shoppingapp.graph
import com.handstandsam.shoppingapp.utils.TextToSpeechEngine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.random.Random


class PokeActivity : ComponentActivity() {

    private val graph: AppGraph get() = application.graph()

    private val textToSpeechEngine by lazy { TextToSpeechEngine(this) }


    enum class PokeBallState {
        Initial, Dragging, Thrown, LandingAnimation, Landed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeechEngine // Initialize
        val mp: MediaPlayer = MediaPlayer.create(this@PokeActivity, R.raw.ding)

        val categoryViewModel = ViewModelProvider(this, graph.viewModelFactory)
            .get(PokeViewModel::class.java)

        fun randomPokemonId(): Int {
            return Random.nextInt(1, 600)
        }

        fun playSound(pokemonName: String) {
            val playSound = false
            if (playSound) {
                mp.seekTo(0)
                mp.start()
                mp.setOnCompletionListener {
                    textToSpeechEngine.speak("A $pokemonName Appeared!")
                }
            }
        }
        setContent {

            var pokeBallState by remember { mutableStateOf(PokeBallState.Initial) }

            val pokeballAnimationDuration = 1200

            var pokeballStateModel by remember { mutableStateOf(PokeBallStateModel()) }


            pokeballStateModel = pokeballStateModel.copy(
                pokeballSizeDp = when (pokeBallState) {
                    PokeBallState.Dragging,
                    PokeBallState.Initial -> INITIAL_POKEBALL_SIZE_DP
                    PokeBallState.LandingAnimation,
                    PokeBallState.Thrown,
                    PokeBallState.Landed -> INITIAL_POKEBALL_SIZE_DP / 2
                }
            )


            var currentOffset = pokeballStateModel.startPositionOffset()
            var currentScale by remember { mutableStateOf(1f) }

            var isDraggingPokeball by remember { mutableStateOf(false) }
            val pokeballOffsetAnimation by animateOffsetAsState(
                targetValue = currentOffset,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            )


            val pokeballScaleAnimationValue: Float by animateFloatAsState(
                targetValue = currentScale,
                animationSpec = tween(pokeballAnimationDuration, easing = FastOutSlowInEasing),
            )

            // Pokemon Info
            var pokemonImageScale by remember { mutableStateOf(1f) }
            val pokemonAnimatedImageScale: Float by animateFloatAsState(
                targetValue = pokemonImageScale,
                animationSpec = tween(250, easing = FastOutSlowInEasing),
            )
            var pokemonInfoState by remember {
                mutableStateOf(
                    PokemonInfoState(
                        pokemonId = 1,
                        pokemonAnimatedImageScale = pokemonAnimatedImageScale
                    )
                )
            }

            var doInfiniteRotate by remember { mutableStateOf(false) }


            fun onRestart() {
                playSound(pokemonInfoState.pokemonName)
                pokemonInfoState = pokemonInfoState.copy(
                    pokemonId = randomPokemonId()
                )
                pokeBallState = PokeBallState.Initial
            }

            when (pokeBallState) {
                PokeBallState.Initial -> {
                    isDraggingPokeball = false
                    pokemonImageScale = 1f
                    currentScale = 1f
                    currentOffset = pokeballStateModel.startPositionOffset()
                    doInfiniteRotate = false
                }
                PokeBallState.Thrown -> {
                    isDraggingPokeball = false
                    doInfiniteRotate = true
                    currentOffset = pokeballStateModel.catchPositionOffset()
                    currentScale = .50f
                }
                PokeBallState.Landed -> {
                    doInfiniteRotate = false
                    pokemonImageScale = 0f
                }

                PokeBallState.LandingAnimation -> TODO()
            }


            val currentRotationAngle by rememberInfiniteTransition().animateFloat(
                initialValue = 0F,
                targetValue = 360F,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = pokeballAnimationDuration,
                        easing = LinearEasing
                    )
                )
            )


            val keyframeAnimation by animateOffsetAsState(
                targetValue = currentOffset,
                animationSpec = keyframes {
                    durationMillis = pokeballAnimationDuration
//                    Offset(currentOffset.x, currentOffset.y * 0f) at 500 with LinearOutSlowInEasing // ms
                    Offset(currentOffset.x, currentOffset.y * 0.30f) at 600 with FastOutLinearInEasing
//                    Offset(currentOffset.x, currentOffset.y * 0f) at 1500 with LinearEasing // ms
                },
                finishedListener = {
                    pokeBallState = PokeBallState.Landed
                }
            )
            val finalAnimation = if (isDraggingPokeball) {
                pokeballOffsetAnimation
            } else {
                keyframeAnimation
            }


            val pokeballSizePx = pokeballStateModel.pokeballSizePx()
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
                                pokeBallState = PokeBallState.Thrown
                            }
                        )
                    }
            ) {
                FieldBackgroundImage()

                CurrentPokemonInfo(
                    modifier = Modifier.align(Alignment.Center),
                    pokemonName = pokemonInfoState.pokemonName,
                    pokemonImageUrl = pokemonInfoState.pokemonImageUrl,
                    pokemonAnimatedImageScale = pokemonAnimatedImageScale,
                    onRestart = {
                        onRestart()
                    },
                )

                with(LocalDensity.current) {
                    Pokeball(
                        sizedp = pokeballStateModel.pokeballSizeDp,
                        modifier = Modifier
                            .offset(
                                x = finalAnimation.x.toDp(),
                                y = finalAnimation.y.toDp(),
                            )
                            .clickable { doInfiniteRotate = !doInfiniteRotate }
                            .scale(pokeballScaleAnimationValue)
                            .rotate(
                                if (doInfiniteRotate) {
                                    currentRotationAngle
                                } else {
                                    0f
                                }
                            )
                    )
                }
            }
        }

        lifecycleScope.launchWhenResumed {
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

    @Composable
    private fun FieldBackgroundImage() {
        Image(
            painter = painterResource(R.drawable.pokecatchbackground),
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Background Image"
        )

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