package com.example.healthtrack.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.healthtrack.model.Workout;
import com.example.healthtrack.model.WorkoutDatabase;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;


public class WorkoutsViewModel extends ViewModel {
    private static final String TAG = "WorkoutsViewModel";

    private List<Workout> allWorkouts = new ArrayList<>();
    private WorkoutDatabase workoutDatabase;

    private  MutableLiveData<List<Workout>> workouts;


    public WorkoutsViewModel(Context context) {
        workouts = new MutableLiveData<>();
        workoutDatabase = new WorkoutDatabase(context);

        retrWorkouts();

    }

    public LiveData<List<Workout>> getWorkouts() {
        return workouts;
    }
    public void retrWorkouts() {
        workoutDatabase.getLastFiveWorkouts(new WorkoutDatabase.OnGetWorkoutsListener() {
            @Override
            public void onSuccess(List<Workout> workoutList) {
                workouts.setValue(workoutList);
                Log.d(TAG, "Successfully fetched last five workouts.");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Failed to fetch last five workouts: " + e.getMessage());
            }
        });
    }
    public boolean isDuplicateWorkout(Workout newWorkout) {
        for (Workout workout : allWorkouts) {
            if (workout.getWorkoutName().equals(newWorkout.getWorkoutName())
                    && workout.getCaloriesPerSet() == newWorkout.getCaloriesPerSet()
                    && workout.getNumReps() == newWorkout.getNumReps()
                    && workout.getNumSets() == newWorkout.getNumSets()) {
                return true;
            }
        }
        return false;
    }


}
