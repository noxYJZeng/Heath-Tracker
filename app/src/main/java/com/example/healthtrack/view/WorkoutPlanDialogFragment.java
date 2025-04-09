package com.example.healthtrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.healthtrack.R;
import com.example.healthtrack.model.User;
import com.example.healthtrack.model.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkoutPlanDialogFragment extends DialogFragment {

    private EditText workoutNameET;
    private EditText caloriesPerSetET;
    private EditText numRepsET;
    private EditText numSetsET;
    private EditText notesET;

    private FragmentActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_workout_plan_dialog, null);

        workoutNameET = view.findViewById(R.id.workoutName);
        caloriesPerSetET = view.findViewById(R.id.caloriesPerSet);
        numRepsET = view.findViewById(R.id.numReps);
        numSetsET = view.findViewById(R.id.numSets);
        notesET = view.findViewById(R.id.additionalNotes);

        builder.setView(view)
                .setMessage("Log Workout")
                .setPositiveButton("Confirm", (dialog, id) -> {
                    String workoutName = workoutNameET.getText().toString().trim();
                    String caloriesPerSet = caloriesPerSetET.getText().toString().trim();
                    String numReps = numRepsET.getText().toString().trim();
                    String numSets = numSetsET.getText().toString().trim();
                    String notes = notesET.getText().toString().trim();

                    if (workoutName.isEmpty() || caloriesPerSet.isEmpty()
                            || numReps.isEmpty() || numSets.isEmpty()) {
                        Toast.makeText(getContext(),
                                "Workout Plan Log Failed: Fill Required Fields",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int numCalsPerSet = Integer.parseInt(caloriesPerSet);
                    int reps = Integer.parseInt(numReps);
                    int sets = Integer.parseInt(numSets);

                    // Ensures username attribute is included in workout plan database entries
                    Workout newWorkoutPlan = new Workout(User.getCurrentUser().getUsername(),
                            workoutName, numCalsPerSet, reps, sets, notes);

                    // Perform database operations on a background thread
                    new Thread(() -> {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference workoutplansRef = database
                                .getReference("workoutplans");
                        workoutplansRef.push().setValue(newWorkoutPlan)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("WorkoutPlanDialogFragment",
                                            "Workout added successfully");
                                    if (mActivity != null) {
                                        mActivity.runOnUiThread(() -> {
                                            Toast.makeText(getContext(),
                                                    "Workout Log Successful",
                                                    Toast.LENGTH_SHORT).show();
                                            dismiss();
                                        });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("WorkoutPlanDialogFragment",
                                            "Failed to add workout: " + e.getMessage());
                                    if (mActivity != null) {
                                        mActivity.runOnUiThread(() -> {
                                            Toast.makeText(getContext(),
                                                    "Workout Log Failed", Toast.LENGTH_SHORT)
                                                    .show();
                                        });
                                    }
                                });
                    }).start();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dismiss());

        return builder.create();
    }
}
