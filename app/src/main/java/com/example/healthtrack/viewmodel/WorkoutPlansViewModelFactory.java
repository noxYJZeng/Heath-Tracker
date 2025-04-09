package com.example.healthtrack.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WorkoutPlansViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public WorkoutPlansViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorkoutPlansViewModel.class)) {
            return (T) new WorkoutPlansViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
