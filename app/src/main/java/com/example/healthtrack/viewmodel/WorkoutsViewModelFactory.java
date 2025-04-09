package com.example.healthtrack.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

//import com.example.healthtrack.model.Workout;

//import java.util.ArrayList;
//import java.util.List;

public class WorkoutsViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public WorkoutsViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorkoutsViewModel.class)) {
            return (T) new WorkoutsViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
