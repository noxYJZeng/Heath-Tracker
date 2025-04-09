package com.example.healthtrack.model;
import java.io.Serializable;



public class Workout implements Serializable{
    private String workoutName;
    private static int caloriesPerSet;
    private int reps;
    private static int sets;
    private String notes;
    private String username;

    // No-argument constructor required for Firebase
    public Workout() {
    }

    public Workout(String username, String workoutName,
                   int caloriesPerSet, int reps, int sets, String notes) {
        this.username = username;
        this.workoutName = workoutName;
        this.caloriesPerSet = caloriesPerSet;
        this.reps = reps;
        this.sets = sets;
        this.notes = notes;
    }

    // Getters and setters for each field
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getCaloriesPerSet() {
        return caloriesPerSet;
    }

    public void setCaloriesPerSet(int caloriesPerSet) {
        this.caloriesPerSet = caloriesPerSet;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getNumReps() {
        return reps;
    }

    public int getNumSets() {
        return sets;
    }

    public boolean isValid() {
        return workoutName != null && !workoutName.isEmpty()
                && notes != null && !notes.isEmpty()
                && caloriesPerSet > 0;
    }

    public String getName() {
        return username;
    }
    // Sets the name.

    public void setName(String username) {
        this.username = username;
    }

   public static int getTotalCalories() {
       return caloriesPerSet * sets;
   }
}