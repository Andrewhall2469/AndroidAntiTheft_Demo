package com.example.app.androidantitheftv2;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCase2ResetWithoutAdmin {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void device_Administration_ResetWithoutPermissionTest() {

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(ViewMatchers.withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatCheckedTextView8 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Master Reset Device"), isDisplayed()));
        appCompatCheckedTextView8.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_reset_device), withText("Reset Device"), isDisplayed()));
        appCompatButton3.perform(click());

    }
}
