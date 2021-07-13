package com.practice.goodbadhabits.ui

import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.Toolbar
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.practice.goodbadhabits.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun appLaunchesSuccessfully(){

        onView(withId(R.id.fab_plus))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))

        onView(withId(R.id.nav_view_drawer))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.main_tool_bar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.bottom_tool_bar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_habit_list))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(MaterialToolbar::class.java))
            ))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))
    }

    @Test
    fun drawerMenuOpenableAndWorkCorrect(){
        onView(withId(R.id.nav_view_drawer))
            .check(matches(not(isDisplayed())))

        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(MaterialToolbar::class.java))
            ))
            .perform(click())

        onView(withId(R.id.nav_view_drawer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.navigation_home))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))

        onView(withId(R.id.navigation_about))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))

        onView(withId(R.id.clearData))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))


        onView(withId(R.id.navigation_about))
            .perform(click())

        onView(withId(R.id.fl_about_screen))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(MaterialToolbar::class.java))
            ))
            .perform(click())

        onView(withId(R.id.navigation_home))
            .perform(click())

        onView(withId(R.id.table_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun viewPagerWork() {


        onView(withId(R.id.view_pager))
            .check(matches(isDisplayed()))
            .check { view, _ -> assert((view as ViewPager2).currentItem == 0) }
            .check { view, _ -> assert((view as ViewPager2).currentItem != 1) }

        onView(withId(R.id.table_layout))
            .perform(swipeLeft())

        onView(withId(R.id.view_pager))
            .check(matches(isDisplayed()))
            .check { view, _ -> assert((view as ViewPager2).currentItem == 1) }
            .check { view, _ -> assert((view as ViewPager2).currentItem != 0) }

        onView(withId(R.id.table_layout))
            .perform(swipeRight())

        onView(withId(R.id.view_pager))
            .check(matches(isDisplayed()))
            .check { view, _ -> assert((view as ViewPager2).currentItem == 0) }
            .check { view, _ -> assert((view as ViewPager2).currentItem != 1) }

    }




}