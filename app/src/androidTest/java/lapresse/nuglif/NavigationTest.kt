package lapresse.nuglif

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import lapresse.nuglif.ui.MainActivity
import lapresse.nuglif.ui.adapter.article.ArticleViewHolder
import lapresse.nuglif.ui.adapter.channel.ChannelPreferenceViewHolder
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testArticleSortingMenu() {
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))

        onView(withId(R.id.action_sort)).perform(click())
        onView(withText(getString(R.string.sort_by_date))).inRoot(RootMatchers.isPlatformPopup()).perform(click())

        onView(withId(R.id.action_sort)).perform(click())
        onView(withText(getString(R.string.sort_by_channel))).inRoot(RootMatchers.isPlatformPopup()).perform(click())
    }

    @Test
    fun navigateToDetailsAndBack(){
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))

        onView(withId(R.id.articleFeedRecyclerView)).perform(ScrollToBottomAction())
        onView(withId(R.id.articleFeedRecyclerView))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ArticleViewHolder>(0, clickItemWithId(R.id.articleItemRoot)))

        onView(withText(getString(R.string.fragment_article_details))).check(matches(isDisplayed()))

        Espresso.pressBack()
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToChannelPreferenceAndBack() {
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))

        // Use the Home button
        onView(withId(R.id.action_filter_by_channel)).perform(click())
        onView(withId(R.id.filterPreferenceHomeButton)).perform(click())
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))

        // Click on the first channel item in the list
        onView(withId(R.id.action_filter_by_channel)).perform(click())
        onView(withId(R.id.filterPreferenceRecyclerview))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ChannelPreferenceViewHolder>(0, click()))
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))

        // Click on the back button
        onView(withId(R.id.action_filter_by_channel)).perform(click())
        Espresso.pressBack()
        onView(withText(getString(R.string.fragment_feed_title))).check(matches(isDisplayed()))
    }

    private fun getString(id: Int): String {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.resources.getString(id)
    }
}