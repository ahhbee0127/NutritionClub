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


public class UserList {
    private ArrayList<User> userList;

    public UserList() {
    }

//    public List<User> getUserList() {
//        return userList;
//    }

    public void add(User user) {
        userList.add(user);
    }

    public User findId(String userId) {
        boolean found = false;
        int i = 0;
        User theUser = null;

        while (i < this.userList.size() && !found) {
            theUser = (User) this.userList.get(i);
            if (theUser.getUserId().equals(userId)) {
                found = true;
            } else {
                ++i;
            }
        }

        return found ? theUser : null;
    }
}

//public class UserList extends ArrayAdapter<User>{
//
//    private Activity context;
//    private List<User> userList;
//
//    public  UserList(){
//    }
//
//    public UserList(Activity context, List<User> userList){
//
//        super(context, R.layout.activity_show_personal,userList);
//        this.context = context;
//        this.userList = userList;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        LayoutInflater inflater = context.getLayoutInflater();
//
//        View listViewItem = inflater.inflate(R.layout.user_list_layout,null,true);
//
//        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.emailV);
////        TextView textViewName = (TextView) listViewItem.findViewById(R.id.nameV);
////        TextView textViewHeight = (TextView) listViewItem.findViewById(R.id.heightV);
////        TextView textViewAge = (TextView) listViewItem.findViewById(R.id.ageV);
////        TextView textViewContact = (TextView) listViewItem.findViewById(R.id.contactV);
////        TextView textViewInviter = (TextView) listViewItem.findViewById(R.id.inviterV);
////        TextView textViewNcBranch = (TextView) listViewItem.findViewById(R.id.ncBranchV);
//
//        User user = userList.get(position);
//
//        textViewEmail.setText(user.getEmail());
//
////        textViewName.setText(user.getName());
////        textViewHeight.setText(String.valueOf(user.getHeight()));
////        textViewAge.setText(String.valueOf(user.getAge()));
////        textViewContact.setText(user.getContact());
////        textViewInviter.setText(user.getInviter());
////        textViewNcBranch.setText(user.getNutritionClub());
//
//        return listViewItem;
//    }
//}