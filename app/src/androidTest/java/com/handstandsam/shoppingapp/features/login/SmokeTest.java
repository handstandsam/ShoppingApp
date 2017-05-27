package com.handstandsam.shoppingapp.features.login;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SmokeTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void smokeTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.handstandsam.shoppingapp.R.id.input_layout_username),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.handstandsam.shoppingapp.R.id.input_layout_username),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("sam"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.handstandsam.shoppingapp.R.id.input_layout_password),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("pass"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.remember_me), withText("Remember Me"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                3)));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.submit), withText("Log in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                4)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.welcome_message), withText("Welcome back Sam Edwards"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Welcome back Sam Edwards")));

        ViewInteraction textView2 = onView(
                allOf(withText("Categories"),
                        childAtPosition(
                                allOf(withId(com.handstandsam.shoppingapp.R.id.action_bar),
                                        childAtPosition(
                                                withId(com.handstandsam.shoppingapp.R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Categories")));

        ViewInteraction recyclerView = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.categories),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.categories),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView3 = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.title_text), withText("Granny Smith Apple"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.handstandsam.shoppingapp.R.id.scroll_container),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Granny Smith Apple")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.add_to_cart), withText("Add to Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.view_cart), withContentDescription("View Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.handstandsam.shoppingapp.R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(com.handstandsam.shoppingapp.R.id.text), withText("1 item(s) in your cart.\n* Granny Smith Apple.\n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("1 item(s) in your cart. * Granny Smith Apple. ")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
