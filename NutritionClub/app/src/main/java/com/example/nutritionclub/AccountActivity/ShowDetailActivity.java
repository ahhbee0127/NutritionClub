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
        import android.widget.Button;
        import android.widget.TextView;

        import com.example.nutritionclub.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class ShowDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String USER_ID = "userId";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    private TextView nameV;
    private TextView heightV;
    private TextView ageV;
    private TextView contactV;
    private TextView inviterV;
    private TextView branchV;
    private TextView genderV;
    private Button dietButton;
    private Button bodyComButton;

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    public static Activity activity;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        activity = this;
        nameV = (TextView) findViewById(R.id.dateV);
        contactV = (TextView) findViewById(R.id.fatkgV);
        ageV = (TextView) findViewById(R.id.weightV);
        inviterV = (TextView) findViewById(R.id.visceralFatV);
        heightV = (TextView) findViewById(R.id.fatpercentV);
        branchV = (TextView) findViewById(R.id.ncV);
        genderV = (TextView) findViewById(R.id.genderV);
        dietButton = (Button) findViewById(R.id.dietButton);
        bodyComButton = (Button) findViewById(R.id.bodyComButton);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

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

        final String userId = getIntent().getStringExtra( ShowAllUserActivity.USER_ID);

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
                        String gender = dataSnapshot.child("gender").getValue(String.class);
                        genderV.setText(gender);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });


        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CoachShowAllDietActivity.class);
                intent.putExtra(USER_ID,userId);
                startActivity(intent);
            }
        });

        bodyComButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String age= getIntent().getStringExtra( ShowAllUserActivity.AGE);
                String gender = getIntent().getStringExtra( ShowAllUserActivity.GENDER);
                Intent intent = new Intent(getApplicationContext(),CoachShowAllBodyActivity.class);
                intent.putExtra(USER_ID,userId);
                //intent.putExtra(AGE,age);
                intent.putExtra(GENDER,gender);
                startActivity(intent);
            }
        });

        hideItem();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String userId = getIntent().getStringExtra( ShowAllUserActivity.USER_ID);
        Intent intent;

        switch (id){

            case R.id.manage_role:
                intent = new Intent(getApplicationContext(),ManageRoleActivity.class);

                intent.putExtra(USER_ID,userId);
                startActivity(intent);
                break;
        }

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
                startActivity(new Intent(ShowDetailActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(ShowDetailActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(ShowDetailActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(ShowDetailActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(ShowDetailActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(ShowDetailActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(ShowDetailActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(ShowDetailActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(ShowDetailActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(ShowDetailActivity.this, InfoCornerActivity.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_role_menu,menu);
        return true;
    }
}

