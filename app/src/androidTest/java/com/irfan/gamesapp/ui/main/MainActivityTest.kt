package com.irfan.gamesapp.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.irfan.gamesapp.R
import com.irfan.gamesapp.utils.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainActivityTest {

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        rule.scenario
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testDisplayHomeScreen() {
        onView(withId(R.id.rvGames)).check(matches(isDisplayed()))
    }

    @Test
    fun testDisplayFavoriteScreen() {
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.rvGames)).check(matches(isDisplayed()))
    }

    @Test
    fun testDisplayDetailGameScreenAndClickFavoriteButton() {
        onView(withId(R.id.rvGames)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
    }

}