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
    String contact;
    String inviter;

    public User(){

    }

    public User(String userId,String email){
        this.userId = userId;
        this.email = email;
    }

    public  User(String name, int age, double height, String contact,String inviter, String nutritionClub){
        this.name = name;
        this.age = age;
        this.height = height;
        this.contact = contact;
        this.inviter = inviter;
        this.nutritionClub = nutritionClub;
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

    public String getContact() {
        return contact;
    }

    public String getInviter() {
        return inviter;
    }
}
