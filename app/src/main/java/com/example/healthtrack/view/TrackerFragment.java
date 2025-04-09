package com.example.healthtrack.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.healthtrack.R;
import com.example.healthtrack.model.Workout;
import com.example.healthtrack.viewmodel.WorkoutsViewModel;
import com.example.healthtrack.viewmodel.WorkoutsViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class TrackerFragment extends Fragment {

    private static final String TAG = "TrackerFragment";

    private ListView workoutListView;
    private WorkoutsAdapter adapter;
    private WorkoutsViewModel viewModel;
    private Button addWorkoutButton;

    public TrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        workoutListView = view.findViewById(R.id.workout_list);
        addWorkoutButton = view.findViewById(R.id.addWorkoutButton);

        adapter = new WorkoutsAdapter(getContext(), R.layout.workout_item, new ArrayList<>(), workout -> {
            Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
            intent.putExtra("workout", workout);//here see my modification in workout class:)
            startActivity(intent);
        });
        workoutListView.setAdapter(adapter);

        WorkoutsViewModelFactory factory = new WorkoutsViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this, factory).get(WorkoutsViewModel.class);
        viewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                adapter.clear();
                adapter.addAll(workouts);
            }
        });

        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutDialogFragment dialogFragment = new WorkoutDialogFragment();
                dialogFragment.show(getParentFragmentManager(), "WorkoutDialogFragment");
            }
        });

        return view;
    }
}