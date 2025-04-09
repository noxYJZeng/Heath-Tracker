package com.example.healthtrack.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtrack.R;
import com.example.healthtrack.model.User;
import com.example.healthtrack.viewmodel.CaloriesFragmentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_healthtrack);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        CaloriesFragmentViewModel viewModel = new ViewModelProvider(this)
                .get(CaloriesFragmentViewModel.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get username and password
                EditText editTextUsername = findViewById(R.id.editTextUsername);
                String username = editTextUsername.getText().toString();
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields must be filled",
                            Toast.LENGTH_SHORT).show();
                } else if (username.contains(" ") || password.contains(" ")) {
                    Toast.makeText(LoginActivity.this,
                            "No spaces allowed in username or password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    database.getReference().child("users").child(username)
                            .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data",
                                                task.getException());
                                    } else {
                                        User user = task.getResult().getValue(User.class);
                                        if (user != null && password.equals(user.getPassword())) {
                                            viewModel.init(user);
                                            Intent intent = new Intent(LoginActivity.this,
                                                    MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(LoginActivity.this,
                                                    "Username or password incorrect.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}