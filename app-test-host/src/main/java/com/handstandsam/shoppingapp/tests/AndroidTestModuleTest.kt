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

/**
 * com.android.test
 */
class AndroidTestModuleTest {

  @Test
  fun androidTestModule() {
    assertTrue(true)
  }

  @Test
  fun androidTestModuleLoginActivityTypeUsernameTest() {
    val activityScenario: ActivityScenario<LoginActivity> =
      ActivityScenario.launch(LoginActivity::class.java)

    activityScenario.moveToState(Lifecycle.State.RESUMED)

    onView(withId(R.id.username)).perform(typeText("username"))

    activityScenario.moveToState(Lifecycle.State.DESTROYED)
  }
}