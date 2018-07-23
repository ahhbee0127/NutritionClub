package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 22/7/2018.
 */

public class User {

    //Account
    String userId;
    String email;
    String role;
    User client;
    User coach;

    //Personal Details
    String name;
    int age;
    double height;
    String nutritionClub;

    public User(){

    }

    public User(String userId,String email){
        this.userId = userId;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public double getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getNutritionClub() {
        return nutritionClub;
    }

    public String getRole() {
        return role;
    }

    public User getCoach() {
        return coach;
    }

    public User getClient() {
        return client;
    }
}
