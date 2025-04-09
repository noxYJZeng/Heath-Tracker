package com.example.healthtrack.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.healthtrack.R;
import com.example.healthtrack.model.User;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.healthtrack.model.User;
import com.example.healthtrack.model.UserDatabase;


public class PersonalInfoFragment extends Fragment {
    private EditText ageEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText genderEditText;
    private Button saveButton;


    private FirebaseAuth auth;
    private DatabaseReference db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        ageEditText = view.findViewById(R.id.ageEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        genderEditText = view.findViewById(R.id.genderEditText);
        saveButton = view.findViewById(R.id.saveButton);

        auth = FirebaseAuth.getInstance();
        db = UserDatabase.getInstance().getDatabaseReference();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePersonalInfo();
            }
        });

        return view;
    }

    private void savePersonalInfo() {

        String ageText = ageEditText.getText().toString().trim();
        String heightText = heightEditText.getText().toString().trim();
        String weightText = weightEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();


        User currUser = User.getCurrentUser();

        if (currUser != null) {

            // Each of these checks for whitespace
            if (!ageText.isEmpty()) {

                int age = Integer.parseInt(ageText);
                currUser.setAge(age);
            }
            if (!heightText.isEmpty()) {

                int height = Integer.parseInt(heightText);
                currUser.setHeight(height);
            }
            if (!weightText.isEmpty()) {

                int weight = Integer.parseInt(weightText);
                currUser.setWeight(weight);
            }
            if (!gender.isEmpty()) {

                currUser.setGender(gender);
            }


            String username = currUser.getUsername();

            if (username != null && !username.isEmpty()) {


                DatabaseReference dbRef = db.child(username);
                dbRef.setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {

                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Personal info saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error saving personal info", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }


            //User user = new User("username", "password", age, height, weight, gender);
            // make this method work

        } else {
            Toast.makeText(getContext(), "User not signed in", Toast.LENGTH_SHORT).show();
        }

    }
}