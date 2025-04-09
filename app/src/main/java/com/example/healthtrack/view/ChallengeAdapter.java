package com.example.healthtrack.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;
import com.example.healthtrack.model.Challenge;

import java.util.HashSet;
import java.util.List;
import android.widget.ArrayAdapter;

public class ChallengeAdapter extends ArrayAdapter<Challenge> {
    private Context context;
    private int resource;
    private List<Challenge> challengeList;
    private OnChallengeClickListener listener;

    public interface OnChallengeClickListener {
        void onChallengeClick(Challenge challenge);
    }

    public ChallengeAdapter(Context context, int resource, List<Challenge> challengeList, OnChallengeClickListener listener) {
        super(context, resource, challengeList);
        this.context = context;
        this.resource = resource;
        this.challengeList = challengeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        Challenge challenge = getItem(position);
        if (challenge != null) {
            TextView challengeTitleTextView = convertView.findViewById(R.id.challengeTitleTextView);
            TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);

            String challengeTitle = challenge.getTitle();
            if (isDuplicate(challengeTitle)) {
                challengeTitle += "*";
            }

            challengeTitleTextView.setText(challengeTitle);
            usernameTextView.setText(challenge.getUsername());


        }

        return convertView;
    }

    private boolean isDuplicate(String challengeTitle) {
        HashSet<String> challengeTitlesSet = new HashSet<>();
        for (Challenge challenge : challengeList) {
            if (challengeTitlesSet.contains(challenge.getTitle())) {
                if (challenge.getTitle().equals(challengeTitle)) {
                    return true;
                }
            } else {
                challengeTitlesSet.add(challenge.getTitle());
            }
        }
        return false;
    }
}
