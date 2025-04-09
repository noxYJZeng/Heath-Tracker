
package com.example.healthtrack.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.healthtrack.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private CaloriesFragment caloriesFragment = new CaloriesFragment();
    private TrackerFragment trackerFragment = new TrackerFragment();
    private WorkoutsFragment workoutsFragment = new WorkoutsFragment();
    private CommunityFragment communityFragment = new CommunityFragment();
    private PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_healthtrack);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, caloriesFragment)
                .commit();
        handleIntent(getIntent());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_calories) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, caloriesFragment).commit();
            return true;
        }
        if (item.getItemId() == R.id.nav_tracker) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, trackerFragment).commit();
            return true;
        }
        if (item.getItemId() == R.id.nav_workouts) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, workoutsFragment).commit();
            return true;
        }
        if (item.getItemId() == R.id.nav_community) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, communityFragment).commit();
            return true;
        }
        if (item.getItemId() == R.id.nav_personal_info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                    personalInfoFragment).commit();
            return true;
        }
        return false;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (intent.hasExtra("OpenFragment")) {
            String fragmentName = intent.getStringExtra("OpenFragment");
            switch (fragmentName) {
                case "TrackerFragment":
                    bottomNavigationView.setSelectedItemId(R.id.nav_tracker);
                    break;
                case "WorkoutsFragment":
                    bottomNavigationView.setSelectedItemId(R.id.nav_workouts);
                    break;
                default:
                    Log.d("MainActivity", "Fragment not recognized: " + fragmentName);
                    break;
            }
        }
    }


    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public CaloriesFragment getCaloriesFragment() {
        return caloriesFragment;
    }

    public TrackerFragment getTrackerFragment() {
        return trackerFragment;
    }

    public WorkoutsFragment getWorkoutsFragment() {
        return workoutsFragment;
    }

    public CommunityFragment getCommunityFragment() {
        return communityFragment;
    }
}
