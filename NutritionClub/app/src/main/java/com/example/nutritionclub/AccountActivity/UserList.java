package com.example.nutritionclub.AccountActivity;

import android.app.Activity;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nutritionclub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AhhBee on 23/7/2018.
 */


public class UserList{
    private ArrayList<User> userList;

    public UserList(){
    }

//    public List<User> getUserList() {
//        return userList;
//    }

    public void add (User user){
        userList.add(user);
    }

    public User findId (String userId){
        boolean found = false;
        int i = 0;
        User theUser = null;

        while(i < this.userList.size() && !found) {
            theUser = (User)this.userList.get(i);
            if (theUser.getUserId().equals(userId)) {
                found = true;
            } else {
                ++i;
            }
        }

        return found ? theUser : null;
    }
}