package com.handstandsam.shoppingapp.features.login


import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.features.home.HomeActivity
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.preferences.UserPreferences
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ErrorScenariosTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java, true, false)

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        val userPreferences = UserPreferences(context)
        userPreferences.currentUser= User("Sam", "Edwards")
        mActivityTestRule.launchActivity(Intent(context, HomeActivity::class.java))
    }

    @Test
    fun http500ErrorTest() {
//        stubFor(get(urlEqualTo("/category/Nintendo/items"))
//                .willReturn(aResponse().withStatus(500)))

        onView(allOf(withId(R.id.categories), isDisplayed()))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withText("Networking Error")).check(matches(isDisplayed()))
    }
}
