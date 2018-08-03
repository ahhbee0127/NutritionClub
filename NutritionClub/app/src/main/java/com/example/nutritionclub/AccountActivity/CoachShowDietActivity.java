package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CoachShowDietActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView dateV;
    private TextView mealV;
    private TextView calV;
    private EditText calF;
    private Button submitButton;

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ImageView imageView;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseDiet;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diet);

        calF = (EditText) findViewById(R.id.calF);
        submitButton = (Button) findViewById(R.id.submitButton);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();

        String currentUserId = getIntent().getStringExtra( CoachShowAllDietActivity.USER_ID);

        mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child(currentUserId);
        dateV = (TextView) findViewById(R.id.dateV);
        mealV = (TextView) findViewById(R.id.mealV);
        calV = (TextView) findViewById(R.id.calV);
        imageView = (ImageView) findViewById(R.id.imageView4);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String dietId = getIntent().getStringExtra(DietDiaryActivity.DIET_ID);

        mDatabaseDiet.child(dietId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String date = dataSnapshot.child("date").getValue(String.class);
                        dateV.setText(date);
                        String meal = dataSnapshot.child("meal").getValue(String.class);
                        mealV.setText(meal);
                        String cal = dataSnapshot.child("calories").getValue(String.class);
                        if (cal == null) {
                            calV.setVisibility(View.GONE);
                            calF.setVisibility(View.VISIBLE);
                            submitButton.setVisibility(View.VISIBLE);
                        } else {
                            calV.setText(cal);
                        }

                        String uri = dataSnapshot.child("image").getValue(String.class);
                        Picasso.with(CoachShowDietActivity.this).load(uri).fit().centerCrop().into(imageView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                calV.setVisibility(View.VISIBLE);
                calF.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
            }
        });
        hideItem();
    }

    public void saveData(){
        final String cal = calF.getText().toString().trim();
        Map<String, Object> dietUpdates = new HashMap<>();
        dietUpdates.put("calories", cal);

        String dietId = getIntent().getStringExtra(DietDiaryActivity.DIET_ID);

        mDatabaseDiet.child(dietId).updateChildren(dietUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CoachShowDietActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                    calV.setText(cal);
                }else{
                    Toast.makeText(CoachShowDietActivity.this,"Error..",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_account:
                startActivity(new Intent(CoachShowDietActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(CoachShowDietActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CoachShowDietActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(CoachShowDietActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(CoachShowDietActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(CoachShowDietActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(CoachShowDietActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(CoachShowDietActivity.this, ShowAllLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(CoachShowDietActivity.this, AnalysisActivity.class));
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideItem() {

        FirebaseUser authUser = auth.getCurrentUser();
        String userId = authUser.getUid();

        mDatabase.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();

                        if (role.equals("coach")) {
                            nav_Menu.findItem(R.id.nav_calFat).setVisible(false);
                        } else if (role.equals("client")) {
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