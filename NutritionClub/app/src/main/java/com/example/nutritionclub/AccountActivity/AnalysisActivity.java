package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.renderscript.Double2;
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

public class AnalysisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;
    private  TextView ageV;
    private  TextView genderV;
    private  TextView fatpercentV;
    private  TextView visceralV;
    private  TextView evaluation1;
    private  TextView evaluation2;

    private TextView bmiV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        bmiV = (TextView) findViewById(R.id.bmiV);
        ageV = (TextView) findViewById(R.id.ageV);
        genderV = (TextView) findViewById(R.id.genderV);
        visceralV = (TextView) findViewById(R.id.visceralV);
        fatpercentV = (TextView) findViewById(R.id.fatPercentV);
        evaluation1 = (TextView) findViewById(R.id.evaluation1V);
        evaluation2 = (TextView) findViewById(R.id.evaluation2V);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userId = firebaseUser.getUid();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navEmail = (TextView) findViewById(R.id.navEmailT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //double bmi = ShowAllBodyActivity.BMI;
//        String bmiS = Double.toString(bmi);
//        bmiV.setText(bmiS);

//        int visceralFat = ShowAllBodyActivity.VISCERAL_FAT;
//        double fatPercent = ShowAllBodyActivity.FAT_PERCENT;

        mDatabaseUser.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int age = dataSnapshot.child("age").getValue(Integer.class);
                        String gender = dataSnapshot.child("gender").getValue(String.class);
                        int visceralFat = ShowAllBodyActivity.VISCERAL_FAT;
                        double fatPercent = ShowAllBodyActivity.FAT_PERCENT;
                        int age1 = age;

                        String age2 = Integer.toString(age1);
                        ageV.setText(age2);
                        genderV.setText(gender);
                        String fatPercent1 = Double.toString(fatPercent);
                        fatpercentV.setText(fatPercent1);
                        String visceral1 = Integer.toString(visceralFat);
                        visceralV.setText(visceral1);

//                        double fatPercent1 = fatPercent;
//                        String fatPercent2 = Double.toString(fatPercent1);
//                        evaluation1.setText(fatPercent2);



                        if(age < 30){
                            if(gender.equals("Male")){
                                if(fatPercent >=14 && fatPercent <= 20){
                                    evaluation1.setText("You are currently in ideal body fat. Which is 14%-20% of fat percentage.");
                                }else if (fatPercent > 25){
                                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 14%-20%.");
                                }else{
                                    evaluation1.setText("You are currently in normal state. Your ideal body fat percent is 14%-20%.");
                                }
                            }else if(gender.equals("Female")){
                                if(fatPercent >=17 && fatPercent <= 24){
                                    evaluation1.setText("You are currently in IDEAL body fat. Which is 17%-24% of fat percentage.");
                                }else if (fatPercent > 30){
                                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 17%-24%.");
                                }else{
                                    evaluation1.setText("You are currently in NORMAL state. Your ideal body fat percent is 17%-24%.");
                                }
                            }
                        }else if(age >= 30){
                            if(gender.equals("Male")){
                                if(fatPercent >=17 && fatPercent <= 23){
                                    evaluation1.setText("You are currently in IDEAL body fat. Which is 17%-23% of fat percentage.");
                                }else if (fatPercent > 25){
                                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 17%-23%.");
                                }else{
                                    evaluation1.setText("You are currently in NORMAL state. Your ideal body fat percent is 17%-23%.");
                                }

                            }else if(gender.equals("Female")){
                                if(fatPercent >=20 && fatPercent <= 27){
                                    evaluation1.setText("You are currently in IDEAL body fat. Which is 20%-27% of fat percentage.");
                                }else if (fatPercent > 30){
                                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 20%-27%.");
                                }else{
                                    evaluation1.setText("You are currently in NORMAL state. Your ideal body fat percent is 20%-27%.");
                                }
                            }
                        }

                        if(visceralFat >= 1 && visceralFat <= 4){
                            evaluation2.setText("Your visceral fat index is in HEALTHY range,whhich is between 1 - 4.");
                        }else if(visceralFat > 4 && visceralFat <= 12 ){
                            evaluation2.setText("Your visceral fat index is in UNHEALTHY range,ideal visceral fat range is 1 - 4.");
                        }else{
                            evaluation2.setText("Your visceral fat index is at HIGH RISK,please consult our coach for more information.Ideal visceral fat range is 1 - 4.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUser:onCancelled", databaseError.toException());
                    }
                });

//
//        if(age < 30){
//            if(gender == "Male"){
//                if(fatPercent >=14 && fatPercent <= 20){
//                    evaluation1.setText("You are currently in ideal body fat. Which is 14%-20% of fat percentage.");
//                }else if (fatPercent > 25){
//                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 14%-20%.");
//                }else{
//                    evaluation1.setText("You are currently in normal state. Your ideal body fat percent is 14%-20%.");
//                }
//            }else if(gender == "Female"){
//                if(fatPercent >=17 && fatPercent <= 24){
//                    evaluation1.setText("You are currently in ideal body fat. Which is 17%-24% of fat percentage.");
//                }else if (fatPercent > 30){
//                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 17%-24%.");
//                }else{
//                    evaluation1.setText("You are currently in normal state. Your ideal body fat percent is 17%-24%.");
//                }
//            }
//        }else if(age >= 30){
//            if(gender == "Male"){
//                if(fatPercent >=17 && fatPercent <= 23){
//                    evaluation1.setText("You are currently in ideal body fat. Which is 17%-23% of fat percentage.");
//                }else if (fatPercent > 25){
//                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 17%-23%.");
//                }else{
//                    evaluation1.setText("You are currently in normal state. Your ideal body fat percent is 17%-23%.");
//                }
//
//            }else if(gender == "Female"){
//                if(fatPercent >=20 && fatPercent <= 27){
//                    evaluation1.setText("You are currently in ideal body fat. Which is 20%-27% of fat percentage.");
//                }else if (fatPercent > 30){
//                    evaluation1.setText("You are currently in OBESITY state. Your ideal body fat percent is 20%-27%.");
//                }else{
//                    evaluation1.setText("You are currently in normal state. Your ideal body fat percent is 20%-27%.");
//                }
//            }
//        }



        hideItem();
    }

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
                startActivity(new Intent(AnalysisActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(AnalysisActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(AnalysisActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(AnalysisActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(AnalysisActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(AnalysisActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(AnalysisActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(AnalysisActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(AnalysisActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(AnalysisActivity.this, InfoCornerActivity.class));
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

        mDatabaseUser.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();

                        if(role.equals("coach")){
                            nav_Menu.findItem(R.id.nav_calFat).setVisible(false);
                            nav_Menu.findItem(R.id.nav_diet).setVisible(false);
                            nav_Menu.findItem(R.id.nav_bodyComposition).setVisible(false);
                        }else if(role.equals("client")){
                            nav_Menu.findItem(R.id.nav_showAllUser).setVisible(false);
                            nav_Menu.findItem(R.id.nav_customerLog).setVisible(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUser:onCancelled", databaseError.toException());
                    }
                });
    }
}
