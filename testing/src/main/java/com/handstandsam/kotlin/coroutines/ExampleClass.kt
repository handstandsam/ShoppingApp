package com.handstandsam.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ExampleClass(scope: CoroutineScope) : CoroutineScope by scope {
    private suspend fun suspendMethod1() {}
    private suspend fun suspendMethod2() {}

    fun doStuff() {
        launch {
            suspendMethod1()
        }
        launch {
            suspendMethod2()
        }
    }
}


