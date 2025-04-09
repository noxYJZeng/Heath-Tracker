package com.example.healthtrack.model;


public class User {
    private static User currentUser;

    private String username;
    private String password;
    private int age;
    private double height;
    private double weight;
    private String gender;
    private double bmr;  // to calculate daily calorie goal

    public User() {}
    //empty constructor needed for viewmodel mutablelivedata

    public User(String username, String password, int age, 
    double height, double weight, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getBmr() {
        return bmr;
    }
    public void setBmr(double bmr) {
        this.bmr = bmr;
    }

    public static User getCurrentUser() {
        return currentUser == null ? new User("Broken User",
                null, 0, 0, 0, null)
         : currentUser;
        //don't want to crash firebase in the event that somehow a user null
        //seeing "broken user" also helps us see when stuff goes wrong
    }

    public static void setCurrentUser(User newUser) {
if (currentUser == null) {
        currentUser = newUser;
}
        //sort of a lazy singleton I guess
    }
}