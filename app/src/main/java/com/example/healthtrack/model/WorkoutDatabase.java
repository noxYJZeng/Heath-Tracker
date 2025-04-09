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

public class WorkoutDatabase {
    private static final String TAG = "WorkoutDatabase";

    private static WorkoutDatabase instance;
    private DatabaseReference databaseReference; //*********
    //private ValueEventListener lastFiveWorkoutsListener;

    private Context context;

    public WorkoutDatabase(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("workouts");

        this.context = context;
    }


    /*
    public static synchronized WorkoutDatabase getInstance() {
        if (instance == null) {
            instance = new WorkoutDatabase();
        }
        return instance;
    }
    */


    public void getLastFiveWorkouts(OnGetWorkoutsListener listener) {
        databaseReference.orderByKey().limitToLast(5)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Workout> workouts = new ArrayList<>();

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Workout workout = dataSnapshot.getValue(Workout.class);
                            workouts.add(workout);
                        }
                        Collections.reverse(workouts);
                        listener.onSuccess(workouts);

                        Log.d(TAG, "getLastFiveWorkouts succeeded");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailure(error.toException());
                        Log.d(TAG, "getLastFiveWorkouts failed");
                    }
                });



    }


    public interface OnGetWorkoutsListener {
        void onSuccess(List<Workout> workouts);
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