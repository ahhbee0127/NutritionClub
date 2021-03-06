package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutritionclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CalculateFatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_fat);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        auth = FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navEmail = (TextView) findViewById(R.id.navEmailT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                startActivity(new Intent(CalculateFatActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(CalculateFatActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CalculateFatActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(CalculateFatActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(CalculateFatActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(CalculateFatActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(CalculateFatActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(CalculateFatActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(CalculateFatActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(CalculateFatActivity.this, InfoCornerActivity.class));
                finish();
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onButtonClick(View v) {
        EditText txtInWeight = (EditText)findViewById(R.id.weightText);
        EditText txtInPercent = (EditText)findViewById(R.id.fatPercentText);
        TextView viewResult = (TextView)findViewById(R.id.result);
        TextView viewOpps = (TextView)findViewById(R.id.opps);

        String weightS = txtInWeight.getText().toString();
        String percentS = txtInPercent.getText().toString();

        if(weightS.equals("") || percentS.equals("")){

            Toast.makeText(this, "Please fill in all the figure for calculation.", Toast.LENGTH_SHORT).show();
            return;

        }else {
            double weight = Double.parseDouble(weightS);
            double percent = Double.parseDouble(percentS);

            double result = (percent / 100) * weight;

            viewResult.setText(Double.toString(result) + " KG");
            viewResult.setVisibility(View.VISIBLE);
            viewOpps.setVisibility(View.VISIBLE);
        }
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
