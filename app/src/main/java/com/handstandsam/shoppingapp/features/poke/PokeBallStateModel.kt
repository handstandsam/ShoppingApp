package com.handstandsam.shoppingapp.features.poke

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


enum class PokeBallState {
    Initial, Dragging, Thrown, Landed
}


data class PokeBallStateModel(
    val pokeballSizeDp: Dp = INITIAL_POKEBALL_SIZE_DP,
    val pokeBallState: PokeBallState = PokeBallState.Initial,
) {

    val isDraggingPokeball: Boolean = when (pokeBallState) {
        PokeBallState.Dragging -> true
        else -> false
    }

    val isPokeballRotating = when (pokeBallState) {
        PokeBallState.Thrown -> true
        PokeBallState.Initial,
        PokeBallState.Dragging,
        PokeBallState.Landed -> false
    }

    @Composable
    private fun getScreenSizePx(): Size {
        val configuration = LocalConfiguration.current
        return with(LocalDensity.current) {
            Size(
                configuration.screenWidthDp.dp.toPx(),
                configuration.screenHeightDp.dp.toPx()
            )
        }
    }

    @Composable
    fun pokeballSizePx() = with(LocalDensity.current) {
        Size(
            width = pokeballSizeDp.toPx(),
            height = pokeballSizeDp.toPx()
        )
    }

    @Composable
    fun startPositionOffset(): Offset {
        val screenSizePx = getScreenSizePx()
        return Offset(
            x = (screenSizePx.width / 2) - (pokeballSizePx().width / 2),
            y = screenSizePx.height - (pokeballSizePx().height * 1.5).toFloat(),
        )
    }

    @Composable
    fun catchPositionOffset(): Offset {
        val screenSizePx = getScreenSizePx()
        return Offset(
            x = (screenSizePx.width / 2) - (pokeballSizePx().width / 2),
            y = with(LocalDensity.current) { 350.dp.toPx() },
        )
    }

    companion object {
        val INITIAL_POKEBALL_SIZE_DP = 150.dp
        val THROWN_POKEBALL_SIZE_DP = INITIAL_POKEBALL_SIZE_DP / 2
    }


}