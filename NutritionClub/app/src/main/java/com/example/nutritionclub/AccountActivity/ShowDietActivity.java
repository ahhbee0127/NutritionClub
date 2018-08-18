package com.example.nutritionclub.AccountActivity;

        import android.content.Intent;
        import android.preference.PreferenceManager;
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
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.nutritionclub.R;
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

public class ShowDietActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String DIET_ID;
    private TextView dateV;
    private TextView mealV;
    private TextView calV;

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

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();
        //getUser();

        FirebaseUser authUser = auth.getCurrentUser();
        final String currentUserId = authUser.getUid();
        //mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child(currentUserId);

        mDatabase.child(currentUserId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();

                        if (role.equals("coach")) {
                            //wantedUserId = getIntent().getStringExtra(ShowAllUserActivity.USER_ID);
                            //mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child(iid);

                        } else if (role.equals("client")) {
                            //wantedUserId = currentUserId;
                            //mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child("K95E8TKycNNnAEyhhw7BqUW8DUS2");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUser:onCancelled", databaseError.toException());
                    }
                });


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
//
//        FirebaseUser authUser = auth.getCurrentUser();
//        String userId = authUser.getUid();


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
                            calV.setText("Waiting for Coach's feedback.");
                        } else {
                            calV.setText(cal);
                        }

                        String uri = dataSnapshot.child("image").getValue(String.class);
                        Picasso.with(ShowDietActivity.this).load(uri).fit().centerCrop().into(imageView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        hideItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String dietId = getIntent().getStringExtra(DietDiaryActivity.DIET_ID);
        Intent intent;

        switch (id) {

            case R.id.edit:
                intent = new Intent(getApplicationContext(), EditDietActivity.class);

                intent.putExtra(DIET_ID, dietId);
                startActivity(intent);
                break;

            case R.id.delete:

                mDatabaseDiet.child(dietId).getRef().removeValue();

                intent = new Intent(getApplicationContext(), DietDiaryActivity.class);
                startActivity(intent);
                finish();
                break;
        }

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
                startActivity(new Intent(ShowDietActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(ShowDietActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(ShowDietActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(ShowDietActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(ShowDietActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(ShowDietActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(ShowDietActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(ShowDietActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(ShowDietActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(ShowDietActivity.this, InfoCornerActivity.class));
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
                            nav_Menu.findItem(R.id.nav_diet).setVisible(false);
                            nav_Menu.findItem(R.id.nav_bodyComposition).setVisible(false);
                        } else if (role.equals("client")) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_menu, menu);
        return true;
    }

}