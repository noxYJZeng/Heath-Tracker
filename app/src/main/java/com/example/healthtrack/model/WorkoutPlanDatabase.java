package com.example.healthtrack.model;


import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class WorkoutPlanDatabase {
    private static final String TAG = "WorkoutPlanDatabase";

    private static WorkoutPlanDatabase instance;
    private DatabaseReference databaseReference;

    private Context context;

    public WorkoutPlanDatabase(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("workoutplans");

        this.context = context;
    }


    /*
    public static synchronized WorkoutPlanDatabase getInstance() {
        if (instance == null) {
            instance = new WorkoutPlanDatabase();
        }
        return instance;
    }
    */


    public void getWorkoutPlans(OnGetWorkoutPlansListener listener) {
        databaseReference.orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Workout> workoutplans = new ArrayList<>();

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Workout workoutplan = dataSnapshot.getValue(Workout.class);
                            workoutplans.add(workoutplan);
                        }
                        Collections.reverse(workoutplans);
                        listener.onSuccess(workoutplans);

                        Log.d(TAG, "getLastFiveWorkoutPlans succeeded");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailure(error.toException());
                        Log.d(TAG, "getLastFiveWorkoutPlans failed");
                    }
                });



    }


    public interface OnGetWorkoutPlansListener {
        void onSuccess(List<Workout> workoutplans);
        void onFailure(Exception e);
    }

    /*
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void addWorkout(Workout workout) {
        databaseReference.push().setValue(workout);
    }

     */
}