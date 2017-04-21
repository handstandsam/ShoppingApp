package com.handstandsam.maintainableespresso.robot;


import android.support.test.espresso.ViewInteraction;

import com.handstandsam.maintainableespresso.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class LoginRobot {

    public LoginRobot username(String username) {
        ViewInteraction usernameEditText = onView(allOf(withId(R.id.username), isDisplayed()));
        usernameEditText.perform(replaceText(username), closeSoftKeyboard());
        return this;
    }

    public LoginRobot password(String password) {
        ViewInteraction passwordEditText = onView(allOf(withId(R.id.password), isDisplayed()));
        passwordEditText.perform(replaceText(password), closeSoftKeyboard());
        return this;
    }

    public LoginRobot toggleRememberMe() {
        ViewInteraction rememberMeCheckbox = onView(allOf(withId(R.id.rememberme), isDisplayed()));
        rememberMeCheckbox.perform(scrollTo(), click());
        return this;
    }


    public LoginRobot login() {
        ViewInteraction submitButton = onView(allOf(withId(R.id.submit), isDisplayed()));
        submitButton.perform(click());
        return this;
    }
}
