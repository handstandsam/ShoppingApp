package com.handstandsam.shoppingapp.tests

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.features.login.LoginActivity
import org.junit.Assert.assertTrue
import org.junit.Test

class TestInLibraryModuleTest {

  @Test
  fun testInLibraryModule() {
    println("Test in Library Module!")
    assertTrue(true)
  }
}