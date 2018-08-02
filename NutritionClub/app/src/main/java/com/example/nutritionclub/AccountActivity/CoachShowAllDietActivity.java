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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.nutritionclub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CoachShowAllDietActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String USER_ID = "userId";
    public static final String DIET_ID = "dietId";
    private DatabaseReference mDatabaseDiet;
    private DatabaseReference mDatabaseUsers;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private FirebaseAuth auth;
    private Button addDiaryButton;

    ListView listViewDiet;
    List<Diet> dietList;
    //ArrayAdapter<User> userAdapter;
    Diet diet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_diary);

        addDiaryButton = (Button) findViewById(R.id.addDiaryButton);

        diet = new Diet();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        USER_ID = getIntent().getStringExtra( ShowAllUserActivity.USER_ID);

        mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child(USER_ID);

        listViewDiet = (ListView) findViewById(R.id.dietDiaryListView);

        auth = FirebaseAuth.getInstance();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dietList = new ArrayList<>();

        addDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoachShowAllDietActivity.this, addDietDiaryActivity.class));
            }
        });

        hideItem();

        listViewDiet.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Diet diet = dietList.get(i);

                Intent intent = new Intent(getApplicationContext(),CoachShowDietActivity.class);

                intent.putExtra(DIET_ID,diet.getId());
                intent.putExtra(USER_ID,USER_ID);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseDiet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dietList.clear();

                for(DataSnapshot dietSnapshot : dataSnapshot.getChildren()){
                    Diet diet = dietSnapshot.getValue(Diet.class);

                    dietList.add(diet);
                }

                DietList adapter = new DietList(CoachShowAllDietActivity.this,dietList);
                listViewDiet.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                startActivity(new Intent(CoachShowAllDietActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(CoachShowAllDietActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CoachShowAllDietActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(CoachShowAllDietActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(CoachShowAllDietActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(CoachShowAllDietActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(CoachShowAllDietActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(CoachShowAllDietActivity.this, CustomerLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(CoachShowAllDietActivity.this, AnalysisActivity.class));
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

        mDatabaseUsers.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();

                        if(role.equals("coach")){
                            nav_Menu.findItem(R.id.nav_calFat).setVisible(false);
                            addDiaryButton.setVisibility(View.GONE);
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


