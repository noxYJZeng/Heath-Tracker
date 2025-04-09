package com.example.healthtrack.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDatabase {
    private static UserDatabase instance;
    private DatabaseReference databaseReference;

    private Firebase db;

    private UserDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void addUser(User newUser, final OnGetUserListener listener) {
        databaseReference.child(newUser.getUsername())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().getValue(User.class);
                            if (user == null) {
                                databaseReference.child(newUser.getUsername()).setValue(newUser);
                                listener.onSuccess();
                            } else {
                                Log.e("firebase",
                                        "tried to create user with username that already exists");
                                listener.onFailed();
                            }
                        } else {
                            Log.e("firebase", "Error getting data",
                                    task.getException());
                        }
                    }
                });
    }
}