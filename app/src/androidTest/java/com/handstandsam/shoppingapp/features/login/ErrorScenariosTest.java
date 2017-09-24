package com.handstandsam.shoppingapp.features.login;


import android.support.test.rule.ActivityTestRule;

import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.features.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.allOf;

public class ErrorScenariosTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule
            = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void http500ErrorTest() {
        stubFor(get(urlEqualTo("/category/Nintendo/items"))
                .willReturn(aResponse().withStatus(500)));

        onView(allOf(withId(R.id.categories), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withText("Networking Error")).check(matches(isDisplayed()));
    }
}
