package com.handstandsam.shoppingapp.compose


import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import com.github.zsoltk.compose.router.Router

interface Root {

    sealed class Routing {
        object UsMap : Routing()

        object State : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            Router(defaultRouting) { backStack ->
                when (val currentRouting = backStack.last()) {
                    is Routing.UsMap -> {
                        Text("Jetpack Compose")
                    }
                    is Routing.State -> {
                    }
                }
            }
        }
    }
}