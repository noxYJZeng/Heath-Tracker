package com.example.healthtrack.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChallengeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ChallengeViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChallengeViewModel.class)) {
            return (T) new ChallengeViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
