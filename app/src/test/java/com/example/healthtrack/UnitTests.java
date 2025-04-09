package com.example.healthtrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.healthtrack.model.Challenge;
import com.example.healthtrack.model.User;
import com.example.healthtrack.model.Workout;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UnitTests {
    @Test
    public void setUserWorksOnce() {
        // this test checks that set user only works if the current user is null
        User user1 = new User("user1",
                "password1",
                12,
                5.5,
                260,
                "M");
        User user2 = new User("user2",
                "password2",
                13,
                76.0,
                286,
                "M");
        User.setCurrentUser(user1);
        User.setCurrentUser(user2);
        assertEquals(user1, User.getCurrentUser());
    }

    @Test
    public void getUserReturnsBrokenUserIfNull() {
        // checks that a "broken user" is returned if the current user reference has not been set
        User brokenUser = User.getCurrentUser();
        assertEquals("Broken User", brokenUser.getUsername());
    }

    @Test
    public void checkBMR() {
        User user = new User();
        assertTrue(user.getBmr() >= 0);
    }

    @Test
    public void getUsername() {
        // Test case for getUsername() method
        User user = new User("testUser",
                "testPassword",
                25,
                6.0,
                180,
                "M");
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void setCurrentUser() {
        // Test case for setCurrentUser() method
        User user1 = new User("user1",
                "password1",
                30,
                5.5,
                160,
                "F");
        User.setCurrentUser(user1);

        User user2 = new User("user2",
                "password2",
                28,
                6.2,
                175,
                "M");
        User.setCurrentUser(user2);

        assertEquals(user1, User.getCurrentUser());
    }

    @Test
    public void testUpdateUserDetails() {
        // Create a new user
        User user = new User("testUser", "testPassword", 25, 1.75, 70, "M");

        // Update the user's weight and height
        user.setWeight(75);
        user.setHeight(1.80);

        // Verify the updates
        assertEquals(75, user.getWeight(), 0.01);
        assertEquals(1.80, user.getHeight(), 0.01);
    }

// Sprint 3 tests
    @Test
    public void testWorkoutInitialization() {
        Workout workout = new Workout("someUser", "NoxFirstDay", 100, 10, 3, "Intense session");
        assertEquals("Workout name should match", "NoxFirstDay", workout.getWorkoutName());
        assertEquals("Calories per set should match", 100, workout.getCaloriesPerSet());
        assertEquals("Reps should match", 10, workout.getReps());
        assertEquals("Sets should match", 3, workout.getSets());
        assertEquals("Notes should match", "Intense session", workout.getNotes());
    }

    @Test
    public void testSettingWorkoutAttributes() {
        Workout workout = new Workout("someUser", "Strength", 200, 5, 5, "Weight lifting");
        workout.setWorkoutName("Updated Strength");
        workout.setCaloriesPerSet(250);
        workout.setReps(6);
        workout.setSets(6);
        workout.setNotes("Updated notes");

        assertEquals("Updated name should be set", "Updated Strength", workout.getWorkoutName());
        assertEquals("Updated calories per set should be set", 250, workout.getCaloriesPerSet());
        assertEquals("Updated reps should be set", 6, workout.getReps());
        assertEquals("Updated sets should be set", 6, workout.getSets());
        assertEquals("Updated notes should be set", "Updated notes", workout.getNotes());
    }

    @Test
    public void testValidWorkout() {
        Workout validWorkout = new Workout("someUser", "Running", 300, 10, 5, "Morning run");
        assertTrue("Workout should be valid", validWorkout.isValid());
    }

    @Test
    public void testWorkoutWithEmptyName() {
        Workout workoutWithEmptyName = new Workout("someUser", "", 300, 10, 5, "Morning run");
        assertFalse("Workout with empty name should be invalid", workoutWithEmptyName.isValid());
    }

    @Test
    public void testUpdateAge() {
        // Create a new user
        User user = new User("testUser", "testPassword", 25, 1.75, 70, "M");

        // Update the user's age
        user.setAge(30);

        // Verify the update
        assertEquals(30, user.getAge());
    }

    @Test
    public void testUpdateWeight() {
        // Create a new user
        User user = new User("testUser", "testPassword", 25, 1.75, 70, "M");

        // Update the user's weight
        user.setWeight(75);

        // Verify the update
        assertEquals(75, user.getWeight(), 0.01);
    }

    @Test
    public void testWorkoutUsername() {
        Workout workout = new Workout("username", "workout", 1, 1, 1, "note");
        assertEquals("username", workout.getUsername());
    }

    @Test
    public void testWorkoutWorkoutName() {
        Workout workout = new Workout("username", "workout", 1, 1, 1, "note");
        assertEquals("workout", workout.getWorkoutName());
    }

    @Test
    public void testChallengeInitialization() {
        Challenge challenge = new Challenge("Fitness Challenge", "","Complete 10,000 steps daily", "12/31", "");

        assertEquals("Title should match", "Fitness Challenge", challenge.getTitle());
        assertEquals("Description should match", "Complete 10,000 steps daily", challenge.getDescription());
        assertEquals("Deadline should match", "12/31", challenge.getDeadline());
    }

    @Test
    public void testSettingChallengeAttributes() {
        Challenge challenge = new Challenge("Initial Title", "", "Initial Description", "01/01", "");

        challenge.setTitle("New Title");
        challenge.setDescription("New Description");
        challenge.setDeadline("07/30");

        assertEquals("Updated title should match", "New Title", challenge.getTitle());
        assertEquals("Updated description should match", "New Description", challenge.getDescription());
        assertEquals("Updated deadline should match", "07/30", challenge.getDeadline());
    }
  
  @Test
    public void testDateFmtCheck() {
        // test logic at view/CreateNewChallengeDialogFragment.java:82
        String d1 = "";
        String d2 = "00/01";
        String d3 = "01/00";
        String d4 = "01/31";
        String d5 = "text";
        String d6 = "02/14";

        String pattern = "(0[1-9]|1[012])\\/(0[1-9]|[12]\\d|3[01])";

        assertFalse(d1.matches(pattern));
        assertFalse(d2.matches(pattern));
        assertFalse(d3.matches(pattern));
        assertFalse(d5.matches(pattern));
        assertTrue(d4.matches(pattern));
        assertTrue(d6.matches(pattern));
    }

    @Test
    public void testDeadlineCheck() {
        // test logic used at view/CreateNewChallengeDialogFragment.java:118
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

        Date deadline;
        try {
            deadline = sdf.parse("07/24");
        } catch (ParseException e) {
            fail();
            return;
        }

        // default is 1970..
        deadline.setYear(Calendar.getInstance()
                .get(Calendar.YEAR) - 1900);

        // change deadline to be 23 hours later
        long ltime = deadline.getTime() + 23 * 60 * 60 * 1000;
        deadline = new Date(ltime);

        Date now = new Date();

        assertTrue(deadline.before(now));
    }
}
