package com.handstandsam.shoppingapp.features.login;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.preferences.UserPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTestCleanedUpWithPreferenceClearing {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        new UserPreferences(context).clearRememberMe();
        mActivityTestRule.launchActivity(new Intent(context, LoginActivity.class));
    }

    @Test
    public void loginActivityTest() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.username), isDisplayed()));
        appCompatEditText.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText2.perform(replaceText("pass"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(allOf(withId(R.id.remember_me), withText("Remember Me")));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.submit), withText("Log in")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction textView = onView(allOf(withId(R.id.welcome_message), withText("Welcome back Sam Edwards"), isDisplayed()));
        textView.check(matches(withText("Welcome back Sam Edwards")));

        ViewInteraction recyclerView = onView(allOf(withId(R.id.categories), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(allOf(withId(R.id.categories), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.add_to_cart), withText("Add to Cart"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction actionMenuItemView = onView(allOf(withId(R.id.view_cart), withContentDescription("View Cart"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView2 = onView(allOf(withId(R.id.item_count), withText("1 item(s) in your cart."), isDisplayed()));
        textView2.check(matches(withText("1 item(s) in your cart.")));
    }
}
