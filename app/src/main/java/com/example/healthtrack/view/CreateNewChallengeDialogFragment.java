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
import com.example.healthtrack.model.Challenge;
import com.example.healthtrack.model.User;
import com.example.healthtrack.model.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateNewChallengeDialogFragment extends DialogFragment {
    private static final String TAG = "CreateNewChallengeDialogFragment";

    private FragmentActivity mActivity;
    private EditText titleET;
    private EditText descriptionET;
    private EditText deadlineET;
    private EditText workoutPlansET;

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
        View view = inflater.inflate(R.layout.fragment_create_new_challenge, null);

        titleET = view.findViewById(R.id.challengeTitle);
        descriptionET = view.findViewById(R.id.challengeDescription);
        deadlineET = view.findViewById(R.id.challengeDeadline);
        workoutPlansET = view.findViewById(R.id.challengeWorkoutPlans);

        builder.setView(view)
                .setMessage("Create New Challenge")
                .setPositiveButton("Confirm", (dialog, id) -> {
                    String title = titleET.getText().toString().trim();
                    String description = descriptionET.getText().toString().trim();
                    String deadline = deadlineET.getText().toString().trim();
                    String workoutPlans = workoutPlansET.getText().toString().trim();

                    if (title.isEmpty() || description.isEmpty() || deadline.isEmpty() || workoutPlans.isEmpty()) {
                        Toast.makeText(getContext(), "Fill Required Fields",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // check deadline against MM/DD format
                    if (!deadline.matches("(0[1-9]|1[012])\\/(0[1-9]|[12]\\d|3[01])")) {
                        Toast.makeText(getContext(), "Deadline must be: MM/DD",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Challenge newChallenge = new Challenge(title, description, deadline, workoutPlans, User.getCurrentUser().getUsername());
                    new Thread(() -> {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference challengesRef = database.getReference("challenges");
                        challengesRef.push().setValue(newChallenge)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Challenge added successfully");
                                    if (mActivity != null) {
                                        mActivity.runOnUiThread(() -> {
                                            Toast.makeText(getContext(),
                                                    "Create new challenge Successful",
                                                    Toast.LENGTH_SHORT).show();
                                            dismiss();
                                        });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Failed to add challenge: " + e.getMessage());
                                    if (mActivity != null) {
                                        mActivity.runOnUiThread(() -> {
                                            Toast.makeText(getContext(),
                                                    "Create new challenge Failed",
                                                    Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                });

                        // check expired challenges
                        challengesRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
                                Date now = new Date();
                                for (DataSnapshot challengeSnapshot : snapshot.getChildren()) {
                                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);
                                    Date challengeDeadline;
                                    try {
                                        challengeDeadline = sdf.parse(challenge.getDeadline());
                                    } catch (ParseException e) {
                                        Log.e(TAG, "Failed to parse date:" + e.getMessage());
                                        return;
                                    }

                                    // default is 1970..
                                    challengeDeadline.setYear(Calendar.getInstance()
                                            .get(Calendar.YEAR) - 1900);

                                    // change deadline to be 23 hours later
                                    long ltime = challengeDeadline.getTime() + 23 * 60 * 60 * 1000;
                                    challengeDeadline = new Date(ltime);

                                    // if now is after the challenge deadline at 11pm
                                    if (now.after(challengeDeadline)) {
                                        challengeSnapshot.getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e(TAG, "Checking expired challenge cancelled"
                                        + error.getMessage());
                            }
                        });
                    }).start();
                    // assuming adding challenge is successful
                    // how do we set the numbers?
                    Workout newWorkoutPlan = new Workout(User.getCurrentUser().getUsername(), title,
                            0, 0, 0, description);
                    new Thread(() -> {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference workoutPlansRef = database.getReference("workoutplans");

                        Query query = workoutPlansRef.orderByChild("workoutName").equalTo(title);
                        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Log.d(TAG, "query finished");
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "query success");
                                    if (task.getResult() != null
                                            && task.getResult().getValue() == null) {
                                        workoutPlansRef.push().setValue(newWorkoutPlan)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("CreateNewChallengeDialogFragment",
                                                            "New challenge added to workout"
                                                                    + "plans successfully");
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("CreateNewChallengeDialogFragment",
                                                            "Failed to add new challenge to"
                                                                    + "workout plans: "
                                                                    + e.getMessage());
                                                });
                                    }
                                } else {
                                    Log.d(TAG, "Error executing query: ", task.getException());
                                }
                            }
                        });
                    }).start();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dismiss());
        return builder.create();
    }
}