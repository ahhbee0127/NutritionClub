package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 25/7/2018.
 */

public class AppController {
    private UserList userList = new UserList();

    public AppController(){

    }

    public void addUser(User user){

    }

    public User findUser(String userId) {
        return this.userList.findId(userId);
    }
}
