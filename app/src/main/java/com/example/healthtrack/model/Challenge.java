package com.example.healthtrack.model;

import java.io.Serializable;

public class Challenge implements Serializable {
    private String title;
    private String description;
    private String deadline;
    private String workoutPlans;

    private String username;

    // req no-arg constructor
    public Challenge() {
    }

    public Challenge(String title, String description, String deadline, String workoutPlans, String username) {
        setTitle(title);
        setDescription(description);
        setDeadline(deadline);
        setWorkoutPlans(workoutPlans);
        setUsername(username);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setWorkoutPlans(String workoutPlans) {
        this.workoutPlans = workoutPlans;
    }

    public void setUsername(String username) { this.username = username;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getWorkoutPlans() {
        return workoutPlans;
    }

    public String getUsername() { return username; }
}
