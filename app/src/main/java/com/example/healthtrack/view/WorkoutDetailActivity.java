package com.example.healthtrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.model.Workout;

public class WorkoutDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Workout workout = (Workout) getIntent().getSerializableExtra("workout");

        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("OpenFragment", "TrackerFragment");
            startActivity(intent);
        });
        TextView tvWorkoutName = findViewById(R.id.tvWorkoutName);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        TextView tvCaloriesPerSet = findViewById(R.id.tvCaloriesPerSet);
        TextView tvReps = findViewById(R.id.tvReps);
        TextView tvSets = findViewById(R.id.tvSets);
        TextView tvNotes = findViewById(R.id.tvNotes);

        if (workout != null) {
            tvAuthor.setText("Author:" + workout.getUsername());
            tvWorkoutName.setText("Workout: " + workout.getWorkoutName());
            tvCaloriesPerSet.setText("Calories: " + workout.getCaloriesPerSet());
            tvReps.setText("Repetitions: " + workout.getReps());
            tvSets.setText("Sets: " + workout.getSets());
            tvNotes.setText("Notes: " + workout.getNotes());
        }

    }

}
