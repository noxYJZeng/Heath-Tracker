package com.example.healthtrack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.healthtrack.model.Challenge;
import com.example.healthtrack.model.ChallengeDatabase;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChallengeViewModel extends ViewModel {
    private static final String TAG = "ChallengeViewModel";

    private List<Challenge> allChallenges = new ArrayList<>();
    private ChallengeDatabase challengeDatabase;
    private MutableLiveData<List<Challenge>> challenges;

    public ChallengeViewModel(Context context) {
        challenges = new MutableLiveData<>();
        challengeDatabase = ChallengeDatabase.getInstance(context);
        retrieveChallenges();
    }

    public LiveData<List<Challenge>> getChallenges() {
        return challenges;
    }

    private void retrieveChallenges() {
        challengeDatabase.getChallenges(new ChallengeDatabase.OnGetChallengesListener() {
            @Override
            public void onSuccess(List<Challenge> challengeList) {
                challenges.setValue(challengeList);
                Log.d(TAG, "Successfully fetched challenges.");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Failed to fetch challenges: " + e.getMessage());
            }
        });
    }

    public boolean isDuplicateChallenge(Challenge newChallenge) {
        for (Challenge challenge : allChallenges) {
            if (challenge.getTitle().equals(newChallenge.getTitle()) &&
                    challenge.getDescription().equals(newChallenge.getDescription()) &&
                    challenge.getDeadline().equals(newChallenge.getDeadline())) {
                return true;
            }
        }
        return false;
    }
}
