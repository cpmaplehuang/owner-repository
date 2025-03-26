package com.example.unscramble


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivity2Test {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity2::class.java)

    @Test
    fun mainActivity2Test() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.user_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.game_card),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("Ws"), closeSoftKeyboard())

//        pressBack()

        val materialButton = onView(
            allOf(
                withId(R.id.submit_button), withText("Submit"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.user_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.game_card),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.user_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.game_card),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText("world"), closeSoftKeyboard())

//        pressBack()

        val materialButton2 = onView(
            allOf(
                withId(R.id.submit_button), withText("Submit"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.user_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.game_card),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText4.perform(scrollTo(), click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.user_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.game_card),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText5.perform(scrollTo(), replaceText("SszA"), closeSoftKeyboard())

//        pressBack()

        val materialButton3 = onView(
            allOf(
                withId(R.id.submit_button), withText("Submit"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton4.perform(scrollTo(), click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton6.perform(scrollTo(), click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton7.perform(scrollTo(), click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton8.perform(scrollTo(), click())

        val materialButton9 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton9.perform(scrollTo(), click())

        val materialButton10 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton10.perform(scrollTo(), click())

        val materialButton11 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton11.perform(scrollTo(), click())

        val materialButton12 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton12.perform(scrollTo(), click())

        val materialButton13 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton13.perform(scrollTo(), click())

        val materialButton14 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton14.perform(scrollTo(), click())

        val materialButton15 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton15.perform(scrollTo(), click())

        val materialButton16 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton16.perform(scrollTo(), click())

        val materialButton17 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton17.perform(scrollTo(), click())

        val materialButton18 = onView(
            allOf(
                withId(R.id.skip_button), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    1
                )
            )
        )
        materialButton18.perform(scrollTo(), click())

        val materialButton19 = onView(
            allOf(
                withId(android.R.id.button1), withText("Play Again"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.constraintlayout.widget.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton19.perform(scrollTo(), click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
