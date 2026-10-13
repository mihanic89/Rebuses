package xyz.yapapa.rebuses;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    private final Context context = ApplicationProvider.getApplicationContext();
    private final int count = RebusRepository.getAll(context).size();

    private String answerAt(int index) {
        return RebusRepository.get(context, index).answer();
    }

    private String titleAt(int index) {
        return context.getString(R.string.rebus_counter, index + 1, count);
    }

    @Test
    public void answerIsHiddenUntilButtonIsClicked() {
        try (ActivityScenario<DetailActivity> ignored =
                     ActivityScenario.launch(DetailActivity.newIntent(context, 0))) {
            onView(allOf(withId(R.id.textView), withText(answerAt(0))))
                    .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
            onView(allOf(withId(R.id.imageView), isDisplayed()))
                    .check(isCompletelyAbove(allOf(withId(R.id.buttonAnswer), isDisplayed())));

            onView(allOf(withId(R.id.buttonAnswer), isDisplayed())).perform(click());

            onView(allOf(withId(R.id.textView), withText(answerAt(0))))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void opensTheRequestedRebus() {
        try (ActivityScenario<DetailActivity> ignored =
                     ActivityScenario.launch(DetailActivity.newIntent(context, 5))) {
            onView(withText(titleAt(5))).check(matches(isDisplayed()));
        }
    }

    @Test
    public void swipingMovesBetweenRebuses() {
        try (ActivityScenario<DetailActivity> ignored =
                     ActivityScenario.launch(DetailActivity.newIntent(context, 0))) {
            onView(withId(R.id.pager)).perform(swipeLeft());
            onView(withText(titleAt(1))).check(matches(isDisplayed()));

            onView(withId(R.id.pager)).perform(swipeRight());
            onView(withText(titleAt(0))).check(matches(isDisplayed()));
        }
    }

    @Test
    public void revealedAnswerSurvivesRecreation() {
        try (ActivityScenario<DetailActivity> scenario =
                     ActivityScenario.launch(DetailActivity.newIntent(context, 1))) {
            onView(allOf(withId(R.id.buttonAnswer), isDisplayed())).perform(click());

            scenario.recreate();

            onView(withText(titleAt(1))).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.textView), withText(answerAt(1))))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void upButtonClosesScreen() {
        try (ActivityScenario<DetailActivity> scenario =
                     ActivityScenario.launch(DetailActivity.newIntent(context, 0))) {
            onView(withContentDescription(androidx.appcompat.R.string.abc_action_bar_up_description))
                    .perform(click());
            // finish() completes asynchronously; wait for the activity to be destroyed.
            long deadline = SystemClock.uptimeMillis() + 10_000;
            while (scenario.getState() != Lifecycle.State.DESTROYED
                    && SystemClock.uptimeMillis() < deadline) {
                SystemClock.sleep(50);
            }
            assertEquals(Lifecycle.State.DESTROYED, scenario.getState());
        }
    }

    @Test
    public void invalidIndexClosesScreen() {
        Intent intent = DetailActivity.newIntent(context, 999);
        try (ActivityScenario<DetailActivity> scenario = ActivityScenario.launch(intent)) {
            assertEquals(Lifecycle.State.DESTROYED, scenario.getState());
        }
    }

    @Test
    public void missingIndexClosesScreen() {
        Intent intent = new Intent(context, DetailActivity.class);
        try (ActivityScenario<Activity> scenario = ActivityScenario.launch(intent)) {
            assertEquals(Lifecycle.State.DESTROYED, scenario.getState());
        }
    }
}
