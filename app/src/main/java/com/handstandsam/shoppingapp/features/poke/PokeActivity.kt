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
import com.handstandsam.shoppingapp.features.poke.PokeBallStateModel.Companion.THROWN_POKEBALL_SIZE_DP
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

            val pokeballAnimationDuration = 1200

            var pokeballStateModel by remember { mutableStateOf(PokeBallStateModel()) }


            var currentOffset by mutableStateOf(pokeballStateModel.startPositionOffset())

            val pokeballOffsetAnimation by animateOffsetAsState(
                targetValue = currentOffset,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            )

            // Pokemon Info
            var pokemonInfoState by remember {
                mutableStateOf(PokemonInfoState())
            }


            fun onRestart() {
                playSound(pokemonInfoState.pokemonName)
                pokemonInfoState = pokemonInfoState.copy(
                    pokemonId = randomPokemonId(),
                )
                pokeballStateModel = pokeballStateModel.copy(pokeBallState = PokeBallState.Initial)
            }

            when (pokeballStateModel.pokeBallState) {
                PokeBallState.Initial -> {
                    pokemonInfoState = pokemonInfoState.copy(
                        pokemonImageScale = 1f
                    )
                    currentOffset = pokeballStateModel.startPositionOffset()
                }
                PokeBallState.Thrown -> {
                    currentOffset = pokeballStateModel.catchPositionOffset()
                }
                PokeBallState.Landed -> {
                    pokemonInfoState = pokemonInfoState.copy(pokemonImageScale = 0f)
                }
                PokeBallState.Dragging -> {
                    currentOffset = currentOffset
                }
            }

            pokeballStateModel = pokeballStateModel.copy(
                pokeballSizeDp = when (pokeballStateModel.pokeBallState) {
                    PokeBallState.Dragging,
                    PokeBallState.Initial -> INITIAL_POKEBALL_SIZE_DP
                    PokeBallState.Thrown,
                    PokeBallState.Landed -> THROWN_POKEBALL_SIZE_DP
                }
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
                    pokeballStateModel = pokeballStateModel.copy(
                        pokeBallState = PokeBallState.Landed
                    )
                }
            )

            val pokeballSizePx = pokeballStateModel.pokeballSizePx()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                if (pokeballStateModel.isDraggingPokeball) {
                                    change.consumeAllChanges()
                                    val newX = (currentOffset.x + dragAmount.x)
                                        .coerceIn(0f, size.width.toFloat() - pokeballSizePx.width)
                                    val newY = (currentOffset.y + dragAmount.y)
                                        .coerceIn(0f, size.height.toFloat() - pokeballSizePx.height)
                                    currentOffset = Offset(newX, newY)
                                }
                            },
                            onDragStart = {
                                if (it.isTouchWithinBounds(currentOffset, pokeballSizePx)) {
                                    println("inside Drag Start")
                                    pokeballStateModel = pokeballStateModel.copy(pokeBallState = PokeBallState.Dragging)
                                } else {
                                    println("outside")
                                }
                            },
                            onDragEnd = {
                                if (pokeballStateModel.isDraggingPokeball) {
                                    pokeballStateModel = pokeballStateModel.copy(pokeBallState = PokeBallState.Thrown)
                                }
                            }
                        )
                    }
            ) {
                FieldBackgroundImage()

                CurrentPokemonInfo(
                    modifier = Modifier.align(Alignment.Center),
                    pokemonName = pokemonInfoState.pokemonName,
                    pokemonImageUrl = pokemonInfoState.pokemonImageUrl,
                    pokemonAnimatedImageScale = pokemonInfoState.pokemonAnimatedImageScale().value,
                    onRestart = {
                        onRestart()
                    },
                )

                val pokeballRotationAngle by rememberInfiniteTransition().animateFloat(
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

                    val pokeballScaleValueAnimated: Float by animateFloatAsState(
                        targetValue = when (pokeballStateModel.pokeBallState) {
                            PokeBallState.Initial,
                            PokeBallState.Dragging -> 1f
                            PokeBallState.Thrown -> 0.50f
                            PokeBallState.Landed -> 0f
                        },
                        animationSpec = tween(pokeballAnimationDuration, easing = FastOutSlowInEasing),
                    )

                    val pokeballAnimatedOffset = when (pokeballStateModel.pokeBallState) {
                        PokeBallState.Initial -> currentOffset
                        PokeBallState.Dragging -> pokeballOffsetAnimation
                        PokeBallState.Thrown -> keyframeAnimation
                        PokeBallState.Landed -> currentOffset
                    }

                    Pokeball(
                        sizedp = pokeballStateModel.pokeballSizeDp,
                        modifier = Modifier
                            .offset(
                                x = pokeballAnimatedOffset.x.toDp(),
                                y = pokeballAnimatedOffset.y.toDp(),
                            )
//                            .clickable { doInfiniteRotate = !doInfiniteRotate }
                            .scale(pokeballScaleValueAnimated)
                            .rotate(
                                if (pokeballStateModel.pokeBallState == PokeBallState.Thrown) {
                                    pokeballRotationAngle
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