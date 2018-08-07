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

public class ShowBodyDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static String BODY_ID1 = "bodyId";
    private TextView dateV;
    private TextView weightV;
    private TextView fatPercentV;
    private TextView fatkgV;
    private TextView visceralV;
    private TextView boneMassV;
    private TextView metaV;
    private TextView muscleV;
    private TextView bmiV;
    private TextView waterV;
    private TextView commentV;
    private Button commentButton;
    private Button editButton;

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private DatabaseReference mDatabase;
    private DatabaseReference getmDatabaseBody;
    private DatabaseReference getDeleteDatabaseBody;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";
    public Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_body_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        String userId = getIntent().getStringExtra( ShowAllBodyActivity.USER_ID);
        getmDatabaseBody = FirebaseDatabase.getInstance().getReference("Body Compositions").child(userId);
        auth = FirebaseAuth.getInstance();

        dateV = (TextView) findViewById(R.id.dateV);
        weightV = (TextView) findViewById(R.id.weightV);
        fatPercentV = (TextView) findViewById(R.id.fatpercentV);
        fatkgV = (TextView) findViewById(R.id.fatkgV);
        visceralV = (TextView) findViewById(R.id.visceralFatV);
        boneMassV = (TextView) findViewById(R.id.boneMassV);
        metaV = (TextView) findViewById(R.id.metaAgeV);
        muscleV = (TextView) findViewById(R.id.muscleV);
        bmiV = (TextView) findViewById(R.id.bmiV);
        waterV = (TextView) findViewById(R.id.waterV);
        commentV = (TextView) findViewById(R.id.commentV);
        commentButton = (Button) findViewById(R.id.commentButton);
        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

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

        String bodyId = getIntent().getStringExtra( ShowAllBodyActivity.BODY_ID);

        getmDatabaseBody.child(bodyId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String date = dataSnapshot.child("todayDate").getValue(String.class);
                        dateV.setText(date);
                        double weightD = dataSnapshot.child("weight").getValue(Double.class);
                        String weight = Double.toString(weightD);
                        weightV.setText(weight);
                        Double fatPercentD = dataSnapshot.child("fatPercent").getValue(Double.class);
                        String fatPercent = Double.toString(fatPercentD);
                        fatPercentV.setText(fatPercent);
                        Double bmiD = dataSnapshot.child("bmi").getValue(Double.class);
                        String bmi = Double.toString(bmiD);
                        bmiV.setText(bmi);
                        Double boneMassD = dataSnapshot.child("boneMass").getValue(Double.class);
                        String boneMass = Double.toString(boneMassD);
                        boneMassV.setText(boneMass);
                        Double fatkgD = dataSnapshot.child("fatkg").getValue(Double.class);
                        String fatkg = Double.toString(fatkgD);
                        fatkgV.setText(fatkg);
                        int metaD = dataSnapshot.child("metabolicAge").getValue(Integer.class);
                        String meta = Integer.toString(metaD);
                        metaV.setText(meta);
                        Double muscleD = dataSnapshot.child("muscle").getValue(Double.class);
                        String muscle = Double.toString(muscleD);
                        muscleV.setText(muscle);
                        int visceralD = dataSnapshot.child("visceralFat").getValue(Integer.class);
                        String visceral = Integer.toString(visceralD);
                        visceralV.setText(visceral);
                        Double waterD = dataSnapshot.child("water").getValue(Double.class);
                        String water = Double.toString(waterD);
                        waterV.setText(water);
                        String comment = dataSnapshot.child("comment").getValue(String.class);
                        if(comment != null){
                            commentV.setVisibility(View.GONE);
                            commentButton.setText("Show Comment");
                            commentButton.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),BodyCommentActivity.class);

                String bodyId = getIntent().getStringExtra( ShowAllBodyActivity.BODY_ID);
                intent.putExtra(BODY_ID1,bodyId);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditBodyActivity.class);

                String bodyId = getIntent().getStringExtra( ShowAllBodyActivity.BODY_ID);
                intent.putExtra(BODY_ID1,bodyId);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bodyId = getIntent().getStringExtra( ShowAllBodyActivity.BODY_ID);
                getmDatabaseBody.child(bodyId).getRef().removeValue();
                            //appleSnapshot.getRef().removeValue();

                Intent intent = new Intent(getApplicationContext(),ShowAllBodyActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
                startActivity(new Intent(ShowBodyDetailActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(ShowBodyDetailActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(ShowBodyDetailActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(ShowBodyDetailActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(ShowBodyDetailActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(ShowBodyDetailActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(ShowBodyDetailActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(ShowBodyDetailActivity.this, ShowAllLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(ShowBodyDetailActivity.this, AnalysisActivity.class));
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
                            editButton.setVisibility(View.GONE);
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

