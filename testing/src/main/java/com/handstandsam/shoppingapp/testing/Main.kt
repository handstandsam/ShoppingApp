@file:JvmName("ConsumingKotlinFlowsKt")

package com.handstandsam.shoppingapp.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext

fun main() = runBlocking<Unit> {
    val numOfEmissions = 5
    val delayMs = 1000.toLong()

    val stateFlow = flow {
        for (i in 1..numOfEmissions) {
            delay(delayMs)
            emit(State(i))
        }
    }
    withContext(Dispatchers.IO){}

    val sideEffectFlow = flow {
        for (i in 1..numOfEmissions) {
            delay(delayMs)
            emit(SideEffect(i.toString()))
        }
    }

    ConsumingKotlinFlows(
        parentScope = this,
        states = stateFlow,
        sideEffects = sideEffectFlow
    )
}