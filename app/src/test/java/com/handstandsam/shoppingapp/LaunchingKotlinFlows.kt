package com.handstandsam.shoppingapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


/** Example State Class */
class State

/** Placeholder SideEffect Class */
class SideEffect

/** Example Class */
class LaunchingKotlinFlows(
    private val states: Flow<State>,
    private val sideEffects: Flow<SideEffect>
) : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    init {
        launch {
            states.collect { println(it) }

            // This code will never run unless the Flow above finishes
            sideEffects.collect { println(it) }
        }

        launch {
            states.onEach { println(it) }.collect()

            // This code will never run unless the Flow above finishes
            sideEffects.onEach { println(it) }.collect()
        }

        states.onEach { println(it) }.launchIn(this)
        sideEffects.onEach { println(it) }.launchIn(this)
    }
}

fun main() {
    flowOf(State())
    println(Dispatchers.Default)
//    LaunchingKotlinFlows(
//        states = flowOf(State()),
//        sideEffects = flowOf(SideEffect())
//    )
}