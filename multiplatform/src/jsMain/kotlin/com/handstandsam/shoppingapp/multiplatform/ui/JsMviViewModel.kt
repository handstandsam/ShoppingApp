package com.handstandsam.shoppingapp.multiplatform.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class JsMviViewModel<State, Intention>(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined),
    initialState: State
) {
    val states = MutableStateFlow(initialState)

    private val intentions = MutableSharedFlow<Intention?>(1)

    init {
        scope.launch {
            intentions.collect { intention ->
                println("Intention: $intention")
                intention?.let {
                    val newState = reduce(states.value, intention)
                    states.emit(newState)
                }
            }
        }
    }

    abstract fun reduce(state: State, intention: Intention): State

    fun sendIntention(intention: Intention) {
        println("sendIntention(${intention!!::class.simpleName}")
        intentions.tryEmit(intention)
    }

}