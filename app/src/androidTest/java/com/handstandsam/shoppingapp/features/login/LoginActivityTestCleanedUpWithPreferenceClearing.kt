package com.handstandsam.shoppingapp.features.login


import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.test.suitebuilder.annotation.LargeTest
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
        val appCompatEditText = onView(allOf<View>(withId(R.id.username), isDisplayed()))
        appCompatEditText.perform(replaceText("sam"), closeSoftKeyboard())

        val appCompatEditText2 = onView(allOf<View>(withId(R.id.password), isDisplayed()))
        appCompatEditText2.perform(replaceText("pass"), closeSoftKeyboard())

        val appCompatCheckBox = onView(allOf<View>(withId(R.id.remember_me), withText("Remember Me")))
        appCompatCheckBox.perform(scrollTo(), click())

        val appCompatButton = onView(allOf<View>(withId(R.id.submit), withText("Log in")))
        appCompatButton.perform(scrollTo(), click())

        val textView = onView(allOf<View>(withId(R.id.welcome_message), withText("Welcome back Sam Edwards"), isDisplayed()))
        textView.check(matches(withText("Welcome back Sam Edwards")))

        val recyclerView = onView(allOf<View>(withId(R.id.categories), isDisplayed()))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val recyclerView2 = onView(allOf<View>(withId(R.id.categories), isDisplayed()))
        recyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val appCompatButton2 = onView(allOf<View>(withId(R.id.add_to_cart), withText("Add to Cart"), isDisplayed()))
        appCompatButton2.perform(click())

        val actionMenuItemView = onView(allOf<View>(withId(R.id.view_cart), withContentDescription("View Cart"), isDisplayed()))
        actionMenuItemView.perform(click())

        val textView2 = onView(allOf<View>(withId(R.id.item_count), withText("1 item(s) in your cart."), isDisplayed()))
        textView2.check(matches(withText("1 item(s) in your cart.")))
    }
}
