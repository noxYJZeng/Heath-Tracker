
package com.example.healthtrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthtrack.R;
import com.example.healthtrack.model.Workout;
import com.example.healthtrack.viewmodel.WorkoutPlansViewModel;
import com.example.healthtrack.viewmodel.WorkoutPlansViewModelFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsFragment extends Fragment {

    private static final String TAG = "WorkoutsFragment";

    private ListView workoutPlanListView;
    private WorkoutsAdapter adapter;
    private WorkoutPlansViewModel viewModel;
    private Button addWorkoutPlanButton;
    private DatabaseReference workoutsRef;

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
     ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        workoutPlanListView = view.findViewById(R.id.workoutplan_list);
        addWorkoutPlanButton = view.findViewById(R.id.addWorkoutPlanButton);

        adapter = new WorkoutsAdapter(getContext(), R.layout.workout_item, new ArrayList<>(), workout -> {
            Intent intent = new Intent(getActivity(), WorkoutPlanDetailActivity.class);
            intent.putExtra("workout", workout);//here see my modification in workout class:)
            startActivity(intent);
        });
        workoutPlanListView.setAdapter(adapter);

        WorkoutPlansViewModelFactory factory = new WorkoutPlansViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this, factory).get(WorkoutPlansViewModel.class);

        // Initialize Firebase Database Reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        workoutsRef = database.getReference("workoutplans");

        // Fetch workout plans and update UI
        viewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutPlans) {
                Log.d(TAG, "Workout plans changed, size: " + workoutPlans.size());
                adapter.clear();
                adapter.addAll(workoutPlans);
                adapter.notifyDataSetChanged();
            }
        });

        addWorkoutPlanButton.setOnClickListener(v -> {
            WorkoutPlanDialogFragment dialogFragment = new WorkoutPlanDialogFragment();
            dialogFragment.show(getParentFragmentManager(), "WorkoutPlanDialogFragment");
        });

        return view;
    }

    // private void updateWorkoutNames(List<Workout> workoutPlans) {
    //     for (Workout workout : workoutPlans) {
    //         String workoutName = workout.getName();
    //         Query query = workoutsRef.orderByChild("name").equalTo(workoutName);
    //         query.addListenerForSingleValueEvent(new ValueEventListener() {
    //             @Override
    //             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    //                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
    //                     DatabaseReference workoutRef = snapshot.getRef();
    //                     workoutRef.child("name").setValue(workoutName + "*").addOnCompleteListener(task -> {
    //                         if (task.isSuccessful()) {
    //                             Log.d(TAG, "Updated workout name: " + workoutName + "*");
    //                         } else {
    //                             Log.e(TAG, "Failed to update workout name: " + workoutName, task.getException());
    //                         }
    //                     });
    //                 }

    //                 // Update the workout list on the main thread
    //                 if (getActivity() != null) {
    //                     getActivity().runOnUiThread(() -> {
    //                         List<Workout> updatedWorkouts = new ArrayList<>();
    //                         for (Workout workout : workoutPlans) {
    //                             String updatedName = workout.getName();
    //                             if (updatedName.equals(workoutName + "*")) {
    //                                 workout.setName(updatedName); // Ensure name with asterisk
    //                             }
    //                             updatedWorkouts.add(workout);
    //                         }
    //                         // Clear and update the adapter on the main thread
    //                         adapter.clear();
    //                         adapter.addAll(updatedWorkouts);
    //                         adapter.notifyDataSetChanged(); // Ensure the adapter updates the ListView
    //                     });
    //                 }
    //             }

    //             @Override
    //             public void onCancelled(@NonNull DatabaseError databaseError) {
    //                 Log.e(TAG, "Error updating workout name: ", databaseError.toException());
    //             }
    //         });
    //     }
    //}
}
