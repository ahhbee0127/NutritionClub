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

public class ShowEventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String EVENT_ID;
    private TextView eNameV;
    private TextView eDateV;
    private TextView eFromV;
    private TextView eToV;
    private TextView eDetailsV;

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ImageView imageView;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseEvent;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        eNameV = (TextView) findViewById(R.id.eNameV);
        eDateV = (TextView) findViewById(R.id.eDateV);
        eFromV = (TextView) findViewById(R.id.eFromV);
        eToV = (TextView) findViewById(R.id.eToV);

        eDetailsV = (TextView) findViewById(R.id.eDetailsV);


        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();

        mDatabaseEvent = FirebaseDatabase.getInstance().getReference("Events");



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String eventId = getIntent().getStringExtra(ActivityBoardActivity.EVENT_ID);
        mDatabaseEvent.child(eventId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("eventName").getValue(String.class);
                        eNameV.setText(name);
                        String date = dataSnapshot.child("date").getValue(String.class);
                        eDateV.setText(date);
                        String timeFrom = dataSnapshot.child("timeFrom").getValue(String.class);
                        eFromV.setText(timeFrom);
                        String timeTo = dataSnapshot.child("timeTo").getValue(String.class);
                        eToV.setText(timeTo);
                        String details = dataSnapshot.child("details").getValue(String.class);
                        eDetailsV.setText(details);
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
        String eventId = getIntent().getStringExtra(ActivityBoardActivity.EVENT_ID);
        Intent intent;

        switch (id) {

            case R.id.edit:
                intent = new Intent(getApplicationContext(), EditEventActivity.class);

                intent.putExtra(EVENT_ID, eventId);
                startActivity(intent);
                break;

            case R.id.delete:

                mDatabaseEvent.child(eventId).getRef().removeValue();

                intent = new Intent(getApplicationContext(), ActivityBoardActivity.class);
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
                startActivity(new Intent(ShowEventActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(ShowEventActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(ShowEventActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(ShowEventActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(ShowEventActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(ShowEventActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(ShowEventActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(ShowEventActivity.this, ShowAllLogActivity.class));
                finish();
                break;
//
//            case R.id.nav_analysis:
//                startActivity(new Intent(ShowEventActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(ShowEventActivity.this, InfoCornerActivity.class));
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
        getMenuInflater().inflate(R.menu.edit_delete_menu,menu);
        return true;
    }

}