package com.example.healthtrack;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.healthtrack.model.User;
import com.example.healthtrack.view.CaloriesFragment;
import com.example.healthtrack.view.MainActivity;
import com.example.healthtrack.viewmodel.CaloriesFragmentViewModel;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CaloriesInstrumentedTests {
    private CaloriesFragment caloriesFragment;
    private CaloriesFragmentViewModel viewModel;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void createCaloriesFragment() {
        caloriesFragment = new CaloriesFragment();
    }

    @Before
    public void createCaloriesViewModel() {
        viewModel = new CaloriesFragmentViewModel();
    }

    // https://stackoverflow.com/a/23467629/8930299
    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    @Test
    public void checkDailyCaloriesGoalFormat() {
        String text = getText(withId(R.id.dailyCalorieGoal));
        assertTrue(text.matches("\\d+\\/\\d+"));
    }

    @Test
    public void getBMRNonNegative() {
        // nox
        // Checks that the Basal Metabolic Rate (BMR) returned by getBmr() is non-negative
        User user = new User("testUser",
                "testPassword",
                25,
                6.0,
                180,
                "M");
        User.setCurrentUser(user);
        viewModel.getDailyCalorieGoal();
        assertTrue(user.getBmr() >= 0);
    }

    @Test
    public void testBmrCalculation() {
        // nox
        // Create a new user
        User user = new User("testUser", "testPassword", 25, 175, 70, "male");
        User.setCurrentUser(user);
        viewModel.getDailyCalorieGoal();

        // Calculate the expected BMR
        double expectedBmr = (10 * 70) + (6.25 * 175) - (5 * 25) + 5;

        // Verify the BMR calculation
        assertEquals(expectedBmr, user.getBmr(), 0.01);
    }

    @Test
    public void testMaleBmr() {
        // ben
        User user = new User("testUser", "testPassword", 25, 175, 70, "male");
        User.setCurrentUser(user);
        viewModel.getDailyCalorieGoal();
        double expectedBmr = (10 * 70) + (6.25 * 175) - (5 * 25) + 5;
        assertEquals(user.getBmr(), expectedBmr, 0.01);
    }

    @Test
    public void testFemaleBmr() {
        // ben
        User user = new User("testUser", "testPassword", 30, 160, 60, "female");
        User.setCurrentUser(user);
        viewModel.getDailyCalorieGoal();
        double expectedBmr = (10 * 60) + (6.25 * 160) - (5 * 30) - 161;
        assertEquals(user.getBmr(), expectedBmr, 0.01);
    }
}