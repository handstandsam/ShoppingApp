package com.handstandsam.shoppingapp.features.login


import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.preferences.UserPreferences
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTestCleanedUpWithPreferenceClearing {

    @Rule @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java, true, false)

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        UserPreferences(context).clearRememberMe()
        mActivityTestRule.launchActivity(Intent(context, LoginActivity::class.java))
    }

    @Test
    fun loginActivityTest() {
        val appCompatEditText = onView(allOf(withId(R.id.username), isDisplayed()))
        appCompatEditText.perform(replaceText("sam"), closeSoftKeyboard())

        val appCompatEditText2 = onView(allOf(withId(R.id.password), isDisplayed()))
        appCompatEditText2.perform(replaceText("pass"), closeSoftKeyboard())

        val appCompatCheckBox = onView(allOf(withId(R.id.remember_me), withText("Remember Me")))
        appCompatCheckBox.perform(scrollTo(), click())

        val appCompatButton = onView(allOf(withId(R.id.submit), withText("Log in")))
        appCompatButton.perform(scrollTo(), click())

        val textView = onView(allOf(withId(R.id.welcome_message), withText("Welcome back Sam Edwards"), isDisplayed()))
        textView.check(matches(withText("Welcome back Sam Edwards")))

        val recyclerView = onView(allOf(withId(R.id.categories), isDisplayed()))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val recyclerView2 = onView(allOf(withId(R.id.categories), isDisplayed()))
        recyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val appCompatButton2 = onView(allOf(withId(R.id.add_to_cart), withText("Add to Cart"), isDisplayed()))
        appCompatButton2.perform(click())

        val actionMenuItemView = onView(allOf(withId(R.id.activity_main_alerts_menu_item), withContentDescription("View Cart"), isDisplayed()))
        actionMenuItemView.perform(click())

        val textView2 = onView(allOf(withId(R.id.item_count), withText("1 item(s) in your cart."), isDisplayed()))
        textView2.check(matches(withText("1 item(s) in your cart.")))
    }
}
