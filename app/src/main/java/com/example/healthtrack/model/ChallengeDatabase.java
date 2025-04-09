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

public class ChallengeDatabase {
    private static final String TAG = "ChallengeDatabase";

    private static ChallengeDatabase instance;
    private DatabaseReference databaseReference;
    private Context context;

    // Private constructor for singleton pattern
    private ChallengeDatabase(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("challenges"); // Ensure this is the correct path in your Firebase database
        this.context = context;
    }

    // Singleton instance accessor
    public static synchronized ChallengeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new ChallengeDatabase(context);
        }
        return instance;
    }

    // Public method to retrieve challenges
    public void getChallenges(OnGetChallengesListener listener) {
        databaseReference.orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Challenge> challenges = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Challenge challenge = dataSnapshot.getValue(Challenge.class);
                            challenges.add(challenge);
                        }
                        Collections.reverse(challenges); // Reverse to display the latest first, if necessary
                        listener.onSuccess(challenges);
                        Log.d(TAG, "Challenges fetched successfully.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailure(error.toException());
                        Log.d(TAG, "Failed to fetch challenges: " + error.getMessage());
                    }
                });
    }

    // Listener interface for database operations
    public interface OnGetChallengesListener {
        void onSuccess(List<Challenge> challenges);
        void onFailure(Exception e);
    }
}
