package com.example.healthtrack.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;
import com.example.healthtrack.model.Workout;

import java.util.HashSet;
import java.util.List;
import android.widget.ArrayAdapter;


public class WorkoutsAdapter extends ArrayAdapter<Workout> {
    private Context context;
    private int resource;
    private List<Workout> workoutList;
    private OnWorkoutClickListener listener;

    public interface OnWorkoutClickListener {
        void onWorkoutClick(Workout workout);
    }

    public WorkoutsAdapter(Context context, int resource, List<Workout> workoutList, OnWorkoutClickListener listener) {
        super(context, resource, workoutList);
        this.context = context;
        this.resource = resource;
        this.workoutList = workoutList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflator.inflate(resource, parent, false);
        }

        Workout workout = getItem(position);
        if (workout != null) {
            TextView workoutNameTextView = convertView.findViewById(R.id.workoutNameTextView);
            TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);

            String workoutName = workout.getWorkoutName();
            if (isDuplicate(workoutName)) {
                workoutName += "*";
            }

            workoutNameTextView.setText(workoutName);
            usernameTextView.setText(workout.getUsername());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onWorkoutClick(workout);
                }
            });
        }

        return convertView;
    }

    private boolean isDuplicate(String workoutName) {
        HashSet<String> workoutNamesSet = new HashSet<>();
        for (Workout workout : workoutList) {
            if (workoutNamesSet.contains(workout.getWorkoutName())) {
                if (workout.getWorkoutName().equals(workoutName)) {
                    return true;
                }
            } else {
                workoutNamesSet.add(workout.getWorkoutName());
            }
        }
        return false;
    }





    /*
    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameTextView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
        }
    }

     */
}