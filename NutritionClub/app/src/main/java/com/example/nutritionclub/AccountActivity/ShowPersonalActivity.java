package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nutritionclub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.R.id.list;

public class ShowPersonalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public String rolee = "role";
    private TextView nameV;
    private TextView heightV;
    private TextView ageV;
    private TextView contactV;
    private TextView inviterV;
    private TextView branchV;


    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";
    public String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_personal);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        nameV = (TextView) findViewById(R.id.dateV);
        contactV = (TextView) findViewById(R.id.fatkgV);
        ageV = (TextView) findViewById(R.id.weightV);
        inviterV = (TextView) findViewById(R.id.visceralFatV);
        heightV = (TextView) findViewById(R.id.fatpercentV);
        branchV = (TextView) findViewById(R.id.boneMassV);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser authUser = auth.getCurrentUser();
        String userId = authUser.getUid();


        mDatabase.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        nameV.setText(name);
                        double heightD = dataSnapshot.child("height").getValue(Double.class);
                        String height = Double.toString(heightD);
                        heightV.setText(height);
                        int ageI = dataSnapshot.child("age").getValue(Integer.class);
                        String age = Double.toString(ageI);
                        ageV.setText(age);
                        String contact = dataSnapshot.child("contact").getValue(String.class);
                        contactV.setText(contact);
                        String inviter = dataSnapshot.child("inviter").getValue(String.class);
                        inviterV.setText(inviter);
                        String branch = dataSnapshot.child("nutritionClub").getValue(String.class);
                        branchV.setText(branch);
//                        String role = dataSnapshot.child("role").getValue(String.class);
//                        roletxt.setText(role);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
//
//        readData(new MyCallback() {
//            @Override
//            public void onCallback(String value) {
//                rolee = value;
//            }
//        });
//
//        haiz.setText(rolee);

//    private void readData(final MyCallback myCallback){
//        FirebaseUser authUser = auth.getCurrentUser();
//        String userId = authUser.getUid();
//        mDatabase.child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String role = dataSnapshot.child("role").getValue(String.class);
//                        roletxt.setText(role);
//
//                        myCallback.onCallback(role);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                    }
//                });
//    }
//
//
//    private interface MyCallback{
//        void onCallback(String value);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id)
        {
            case R.id.nav_account:
                startActivity(new Intent(ShowPersonalActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(ShowPersonalActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(ShowPersonalActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(ShowPersonalActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(ShowPersonalActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(ShowPersonalActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(ShowPersonalActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(ShowPersonalActivity.this, ShowAllLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(ShowPersonalActivity.this, AnalysisActivity.class));
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideItem()
    {

        FirebaseUser authUser = auth.getCurrentUser();
        String userId = authUser.getUid();

        mDatabase.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        userRole = role;
                        navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();

                        if(role.equals("coach")){
                            nav_Menu.findItem(R.id.nav_calFat).setVisible(false);
                        }else if(role.equals("client")){
                            nav_Menu.findItem(R.id.nav_showAllUser).setVisible(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUser:onCancelled", databaseError.toException());
                    }
                });
    }
}
