package id.my.tabin.ligabola

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.my.tabin.ligabola.activity.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchEventTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchEvent() {
        Thread.sleep(1000)
        onView(withId(R.id.action_search)).perform(click())
        Thread.sleep(1000)
        onView(allOf(withId(R.id.searchMenu), isClickable())).perform(click())
        onView(withId(android.support.design.R.id.search_src_text)).perform(click())
            .perform(typeTextIntoFocusedView("Chelsea"))
        onView(withId(android.support.design.R.id.search_src_text)).perform(
            pressImeActionButton(),
            closeSoftKeyboard()
        )
        Thread.sleep(30000)
        onView(allOf(withId(R.id.recycler_search_list_match))).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
        Thread.sleep(3000)
        onView(allOf(withId(R.id.recycler_search_list_match))).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )
        Thread.sleep(3000)
        onView(isRoot()).perform(ViewActions.pressBack())
        Thread.sleep(500)
        onView(isRoot()).perform(ViewActions.pressBack())
        Thread.sleep(500)
        onView(isRoot()).perform(ViewActions.pressBack())
        Thread.sleep(3000)
    }
}