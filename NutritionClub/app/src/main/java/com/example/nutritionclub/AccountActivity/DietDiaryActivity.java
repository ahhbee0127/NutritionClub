//package com.example.nutritionclub.AccountActivity;
//
//        import android.content.Intent;
//        import android.provider.ContactsContract;
//        import android.support.design.widget.NavigationView;
//        import android.support.v4.view.GravityCompat;
//        import android.support.v4.widget.DrawerLayout;
//        import android.support.v7.app.ActionBarDrawerToggle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//        import android.support.v7.widget.Toolbar;
//        import android.util.Log;
//        import android.view.Menu;
//        import android.view.MenuItem;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.TextView;
//
//        import com.example.nutritionclub.R;
//        import com.google.firebase.auth.FirebaseAuth;
//        import com.google.firebase.auth.FirebaseUser;
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//public class DietDiaryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
//
//    NavigationView navigationView;
//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mToggle;
//    private Toolbar mToolbar;
//    private DatabaseReference mDatabase;
//    private DatabaseReference mDatabaseUser;
//    private FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_diet_diary);
//
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
//
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//
//        mToolbar = (Toolbar) findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);
//
//        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
//        auth = FirebaseAuth.getInstance();
//
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        hideItem();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(mToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item){
//        int id = item.getItemId();
//        switch (id)
//        {
//            case R.id.nav_account:
//                startActivity(new Intent(DietDiaryActivity.this, MainActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_me:
//                startActivity(new Intent(DietDiaryActivity.this, ShowPersonalActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_calFat:
//                startActivity(new Intent(DietDiaryActivity.this, CalculateFatActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_showAllUser:
//                startActivity(new Intent(DietDiaryActivity.this, ShowAllUserActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_bodyComposition:
//                startActivity(new Intent(DietDiaryActivity.this, ShowAllBodyActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_diet:
//                startActivity(new Intent(DietDiaryActivity.this, DietDiaryActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_activityBoard:
//                startActivity(new Intent(DietDiaryActivity.this, ActivityBoardActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_customerLog:
//                startActivity(new Intent(DietDiaryActivity.this, CustomerLogActivity.class));
//                finish();
//                break;
//
//            case R.id.nav_analysis:
//                startActivity(new Intent(DietDiaryActivity.this, AnalysisActivity.class));
//                finish();
//                break;
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    private void hideItem()
//    {
//
//        FirebaseUser authUser = auth.getCurrentUser();
//        String userId = authUser.getUid();
//
//        mDatabaseUser.child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String role = dataSnapshot.child("role").getValue(String.class);
//                        navigationView = (NavigationView) findViewById(R.id.nav_view);
//                        Menu nav_Menu = navigationView.getMenu();
//
//                        if(role.equals("coach")){
//                            nav_Menu.findItem(R.id.nav_calFat).setVisible(false);
//                        }else if(role.equals("client")){
//                            nav_Menu.findItem(R.id.nav_showAllUser).setVisible(false);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w("getUser:onCancelled", databaseError.toException());
//                    }
//                });
//    }
//}



package com.example.nutritionclub.AccountActivity;

import android.app.Activity;
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

public class DietDiaryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DIET_ID = "dietId";
    //public static final String USER_ID = "userId";
    private DatabaseReference mDatabaseDiet;
    private DatabaseReference mDatabaseUsers;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private FirebaseAuth auth;
    private Button addDiaryButton;
    public static Activity activity;

    ListView listViewDiet;
    List<Diet> dietList;
    //ArrayAdapter<User> userAdapter;
    Diet diet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_diary);

        activity = this;
        addDiaryButton = (Button) findViewById(R.id.addDiaryButton);

        diet = new Diet();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        //Later when comes to Admin View different.
        FirebaseUser user = auth.getCurrentUser();
        final String userId = user.getUid();

        mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child(userId);

        listViewDiet = (ListView) findViewById(R.id.dietDiaryListView);


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
                startActivity(new Intent(DietDiaryActivity.this, addDietDiaryActivity.class));
            }
        });

        hideItem();

        listViewDiet.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Diet diet = dietList.get(i);

                Intent intent = new Intent(getApplicationContext(),ShowDietActivity.class);

                intent.putExtra(DIET_ID,diet.getId());
                //intent.putExtra(BODY_ID,bodyComposition.getBodyId());

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

                DietList adapter = new DietList(DietDiaryActivity.this,dietList);
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
                startActivity(new Intent(DietDiaryActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(DietDiaryActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(DietDiaryActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(DietDiaryActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(DietDiaryActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(DietDiaryActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(DietDiaryActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(DietDiaryActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(DietDiaryActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(DietDiaryActivity.this, InfoCornerActivity.class));
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


