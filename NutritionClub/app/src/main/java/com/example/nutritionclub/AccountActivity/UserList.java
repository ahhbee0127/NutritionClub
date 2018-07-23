package com.example.nutritionclub.AccountActivity;

import android.app.Activity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nutritionclub.R;

import java.util.List;

/**
 * Created by AhhBee on 23/7/2018.
 */

public class UserList extends ArrayAdapter<User>{

    private Activity context;
    private List<User> userList;

    public UserList(Activity context, List<User> userList){
        super(context, R.layout.list_layout,)
    }

}
