package com.example.healthtrack.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.healthtrack.model.Workout;
import com.example.healthtrack.model.WorkoutPlanDatabase;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;

public class WorkoutPlansViewModel extends ViewModel {
    private static final String TAG = "WorkoutPlansViewModel";

    private List<Workout> allWorkoutPlans = new ArrayList<>();
    private WorkoutPlanDatabase workoutPlanDatabase;

    private  MutableLiveData<List<Workout>> workoutplans;


    public WorkoutPlansViewModel(Context context) {

        workoutplans = new MutableLiveData<>();
        workoutPlanDatabase = new WorkoutPlanDatabase(context);

        retrWorkoutPlans();

    }


    public LiveData<List<Workout>> getWorkouts() {
        return workoutplans;
    }

    public void retrWorkoutPlans() {
        workoutPlanDatabase.getWorkoutPlans(
                new WorkoutPlanDatabase.OnGetWorkoutPlansListener() {
                    @Override
                    public void onSuccess(List<Workout> workoutList) {
                        workoutplans.setValue(workoutList);
                        Log.d(TAG, "Successfully fetched workout plans.");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "Failed to fetch workout plans: " + e.getMessage());
                    }
                });
    }
    public boolean isDuplicateWorkoutPlan(Workout newWorkoutPlan) {
        for (Workout workoutPlan : allWorkoutPlans) {
            if (workoutPlan.getWorkoutName().equals(newWorkoutPlan.getWorkoutName())
                    && workoutPlan.getCaloriesPerSet() == newWorkoutPlan.getCaloriesPerSet()
                    && workoutPlan.getNumReps() == newWorkoutPlan.getNumReps()
                    && workoutPlan.getNumSets() == newWorkoutPlan.getNumSets()) {
                return true;
            }
        }
        return false;
    }


}
