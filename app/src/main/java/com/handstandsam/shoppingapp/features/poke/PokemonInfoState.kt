package com.handstandsam.shoppingapp.features.poke

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class PokemonInfoState(
    val pokemonId: Int = 1,
    val pokemonImageScale: Float = 1f,
) {
    @Composable
    fun pokemonAnimatedImageScale(): State<Float> {
//        var pokemonImageScale by remember { mutableStateOf(1f) }
        return animateFloatAsState(
            targetValue = pokemonImageScale,
            animationSpec = tween(250, easing = FastOutSlowInEasing),
        )
    }

    val pokemonName: String get() = PokemonNames[pokemonId - 1]
    val pokemonImageUrl: String get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
}