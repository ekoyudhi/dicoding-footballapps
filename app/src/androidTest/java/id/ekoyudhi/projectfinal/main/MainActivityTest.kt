package id.ekoyudhi.projectfinal.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import id.ekoyudhi.projectfinal.R

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testAppBehaviour() {

        onView(withId(R.id.list_event_next)).check(matches(isDisplayed()))
        onView(withId(R.id.list_event_next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.list_event_next)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click())
        )

        onView(withId(R.id.add_to_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorites)).perform(click())
        onView(withText("Added to favorites")).check(matches(isDisplayed()))
        pressBack()

        onView(withText("LAST")).check(matches(isDisplayed()))
        onView(withText("LAST")).perform(click())

        onView(withId(R.id.list_event_past)).check(matches(isDisplayed()))
        onView(withId(R.id.list_event_past)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(R.id.list_event_past)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click())
        )

        onView(withId(R.id.add_to_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorites)).perform(click())
        onView(withText("Added to favorites")).check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.nav_teams)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_teams)).perform(click())

        onView(withId(R.id.list_event)).check(matches(isDisplayed()))
        onView(withId(R.id.list_event)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.list_event)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        onView(withId(R.id.add_to_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorites)).perform(click())
        onView(withText("Added to favorites")).check(matches(isDisplayed()))

        onView(withText("PLAYERS")).check(matches(isDisplayed()))
        onView(withText("PLAYERS")).perform(click())

        onView(withId(R.id.list_event_players)).check(matches(isDisplayed()))
        onView(withId(R.id.list_event_players)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        onView(withId(R.id.list_event_players)).perform(
               RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )

        pressBack()
        pressBack()

        onView(withId(R.id.nav_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_favorites)).perform(click())

        onView(withId(R.id.list_event_matches)).check(matches(isDisplayed()))
        onView(withId(R.id.list_event_matches)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.list_event_matches)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.add_to_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorites)).perform(click())
        onView(withText("Removed from favorites")).check(matches(isDisplayed()))
        pressBack()


        onView(withText("TEAMS")).check(matches(isDisplayed()))
        onView(withText("TEAMS")).perform(click())


        onView(withId(R.id.list_event_teams)).check(matches(isDisplayed()))
        onView(withId(R.id.list_event_teams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.list_event_teams)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.add_to_favorites)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorites)).perform(click())
        onView(withText("Removed from favorites")).check(matches(isDisplayed()))
        pressBack()

    }
}