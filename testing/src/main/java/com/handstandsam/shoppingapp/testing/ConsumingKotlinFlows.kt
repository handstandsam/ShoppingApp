package com.handstandsam.shoppingapp.testing

import com.handstandsam.shoppingapp.testing.TextColors.BLUE
import com.handstandsam.shoppingapp.testing.TextColors.GREEN
import com.handstandsam.shoppingapp.testing.TextColors.RED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MyRepo {
    suspend fun suspendMethod() {}

    fun doStuff() {
        suspendMethod()
    }
}

/** Example Class */
class ConsumingKotlinFlows(
    parentScope: CoroutineScope,
    private val states: Flow<State>,
    private val sideEffects: Flow<SideEffect>
) {
//   : CoroutineScope by CoroutineScope(Dispatchers.Main)

    private val scope = parentScope + Dispatchers.Default

    suspend fun suspendMethod() {}

    fun doStuff() {
        suspendMethod()
        CoroutineScope(Dispatchers.Default).launch {
        }

    }

    val startTime = System.currentTimeMillis()

    enum class TestGroup(val color: String) { A(RED), B(BLUE), C(GREEN) }

    private fun printA(message: String) = print(TestGroup.A, message)
    private fun printB(message: String) = print(TestGroup.B, message)
    private fun printC(message: String) = print(TestGroup.C, message)

    private fun print(testGroup: TestGroup, message: String) {
        val elapsedTime = System.currentTimeMillis() - startTime
        println("${testGroup.color}${testGroup.name} $elapsedTime $message${TextColors.RESET}")
    }

    init {
        println("BEGINNING of init {}")
        /** A */
        scope.launch {
            println("Starting ${TestGroup.A}")
            states.collect { printA("* A * scope.launch $it") }

            // This code will never run unless the Flow above finishes
            sideEffects.onCompletion {
                printA("Finished!")
            }.collect { printA("* A * scope.launch $it") }
            print("********** A **********")
        }

        /** B */
        scope.launch {
            println("Starting ${TestGroup.B}")
            states.onEach { printB("** B ** scope.launch, scope.launch $it") }.collect()
        }
        scope.launch {
            sideEffects.onEach { printB("** B ** scope.launch, scope.launch $it") }.onCompletion {
                printB("Finished!")
            }.collect()
        }

        /** C */
        println("Starting ${TestGroup.C}")
        states.onEach { printC("*** C *** launchIn(scope) $it") }.launchIn(scope)
        sideEffects.onEach { printC("*** C *** launchIn(scope) $it") }.onCompletion {
            printC("Finished!")
        }.launchIn(scope)
        println("END of init {}")
    }
}