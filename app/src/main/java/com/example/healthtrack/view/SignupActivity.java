package com.example.healthtrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtrack.model.OnGetUserListener;
import com.example.healthtrack.model.User;
import com.example.healthtrack.R;
import com.example.healthtrack.model.UserDatabase;
public class SignupActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText genderEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_healthtrack);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.signupUsernameEditText);
        passwordEditText = findViewById(R.id.signupPasswordEditText);
        ageEditText = findViewById(R.id.signupAgeEditText);
        heightEditText = findViewById(R.id.signupHeightEditText);
        weightEditText = findViewById(R.id.signupWeightEditText);
        genderEditText = findViewById(R.id.signupGenderEditText);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = userFromString(usernameEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        ageEditText.getText().toString().trim(),
                        heightEditText.getText().toString().trim(),
                        weightEditText.getText().toString().trim(),
                        genderEditText.getText().toString().trim());

                if (newUser != null) {
                    registerUser(newUser);
                }
            }
        });
    }

    private void registerUser(User newUser) {
        UserDatabase userDatabase = UserDatabase.getInstance();
        OnGetUserListener listener = new OnGetUserListener() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess() {
                User.setCurrentUser(newUser);
                Toast.makeText(SignupActivity.this, "Registration successful",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailed() {
                Toast.makeText(SignupActivity.this, "Username already exists",
                        Toast.LENGTH_SHORT).show();
            }
        };

        userDatabase.addUser(newUser, listener);
    }

    public User userFromString(String username, String password, String ageString,
                               String heightString, String weightString, String gender) {
        if (username.isEmpty() || password.isEmpty() || ageString.isEmpty()
                || heightString.isEmpty() || weightString.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this,
                    "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else if (username.contains(" ") || password.contains(" ")) {
            Toast.makeText(this,
                    "No spaces allowed in username or password", Toast.LENGTH_SHORT).show();
        } else if (!(gender.equals("male") || gender.equals("female"))) {
            Toast.makeText(this,
                    "Gender must be specified as 'male' or 'female'",
                    Toast.LENGTH_SHORT).show();
        } else {
            int age;
            try {
                age = Integer.parseInt(ageString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Age must be a whole number.",
                        Toast.LENGTH_SHORT).show();
                return null;
            }

            double height = Double.parseDouble(heightString);
            double weight = Double.parseDouble(weightString);

            User newUser = new User(username, password, age, height, weight, gender);

            return newUser;
        }

        return null;
    }
}