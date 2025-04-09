package com.example.healthtrack.viewmodel;


import androidx.lifecycle.ViewModel;

import com.example.healthtrack.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CaloriesFragmentViewModel extends ViewModel {
    // private final MutableLiveData<User> user = new MutableLiveData<>(User.getCurrentUser());

    public void init(User user) {
        User.setCurrentUser(user);
    }

    public void getDailyCalorieGoal() {
        // User currUser = user.getValue();
        User currUser = User.getCurrentUser();
        if (currUser != null) {
            // using Mifflin-St Jeor Equation
            double bmr;
            bmr = 10 * currUser.getWeight() + 6.25 * currUser.getHeight() - 5 * currUser.getAge();
            if (currUser.getGender() == "male") {
                bmr += 5;
            } else {
                bmr -= 161;
            }
            currUser.setBmr(bmr);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String dbpath = "users/" + currUser.getUsername() + "/bmr";
            ref.child(dbpath).setValue(bmr);
            // Example of setting a strategy
            // You can change the strategy based on user's goal
            //calorieGoalContext.setStrategy(new WeightLossStrategy());
            //return calorieGoalContext.executeStrategy(currUser);
        }
    }
}
