package com.handstandsam.maintainableespresso;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.handstandsam.maintainableespresso.features.login.LoginActivity;
import com.handstandsam.maintainableespresso.robot.LoginRobot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        ViewInteraction usernameEditText = onView(allOf(withId(R.id.username), isDisplayed()));
        usernameEditText.perform(replaceText("handstandsam"), closeSoftKeyboard());

        ViewInteraction passwordEditText = onView(allOf(withId(R.id.password), isDisplayed()));
        passwordEditText.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction rememberMeCheckbox = onView(allOf(withId(R.id.remember_me), isDisplayed()));
        rememberMeCheckbox.perform(scrollTo(), click());

        ViewInteraction submitButton = onView(allOf(withId(R.id.submit), isDisplayed()));
        submitButton.perform(click());

        ViewInteraction categoriesRecyclerView = onView(allOf(withId(R.id.categories), isDisplayed()));
        categoriesRecyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void loginActivityTestWithRobot() {
        new LoginRobot()
                .username("handstandsam")
                .password("password")
                .toggleRememberMe()
                .login();
    }
}
