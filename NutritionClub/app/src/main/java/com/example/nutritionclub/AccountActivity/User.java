package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 22/7/2018.
 */

public class User {

    String userId;
    String email;

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
}
