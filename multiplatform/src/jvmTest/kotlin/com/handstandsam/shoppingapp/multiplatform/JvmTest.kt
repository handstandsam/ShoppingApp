package com.handstandsam.shoppingapp.multiplatform

import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check JVM is mentioned", Greeting().greeting().contains("JVM"))
    }
}