package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.provider.ContactsContract;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomerLogActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseCheckin;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;
    private EditText nameF;
    private EditText timeF;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log);

        nameF = (EditText) findViewById(R.id.nameF);
        timeF = (EditText) findViewById(R.id.timeF);
        saveButton = (Button) findViewById(R.id.saveButton);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        mDatabaseCheckin = FirebaseDatabase.getInstance().getReference("Customer Log");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navEmail = (TextView) findViewById(R.id.navEmailT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hideItem();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCheckin();
                startActivity(new Intent(CustomerLogActivity.this,ShowAllBodyActivity.class));
                finish();
            }
        });

    }

    protected void saveCheckin(){
        String name = nameF.getText().toString().trim();
        String time = timeF.getText().toString().trim();

        String date = getIntent().getStringExtra( LogCalenderActivity.DATE);
        final Checkin checkinData = new Checkin(date,name,time);
        String checkinId = mDatabaseCheckin.push().getKey();
        mDatabaseCheckin.child(date).child(checkinId).setValue(checkinData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CustomerLogActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CustomerLogActivity.this,"Error..",Toast.LENGTH_LONG).show();
                }
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
                startActivity(new Intent(CustomerLogActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(CustomerLogActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CustomerLogActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(CustomerLogActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(CustomerLogActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(CustomerLogActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(CustomerLogActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(CustomerLogActivity.this, CustomerLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(CustomerLogActivity.this, AnalysisActivity.class));
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
