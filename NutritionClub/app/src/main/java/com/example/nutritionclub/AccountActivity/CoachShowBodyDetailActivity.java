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

public class CoachShowBodyDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static String USER_ID1 = "userId";
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
    private Button commentButton;
    //private Button showCommentButton;
    private TextView commentV;

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private DatabaseReference mDatabase;
    private DatabaseReference getmDatabaseBody;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_body_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        String userId = getIntent().getStringExtra( CoachShowAllBodyActivity.USER_ID);
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
        commentButton = (Button) findViewById(R.id.commentButton);
        //showCommentButton = (Button) findViewById(R.id.showCommentButton);
        commentV = (TextView) findViewById(R.id.commentV);

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
                        Double metaD = dataSnapshot.child("metabolicAge").getValue(Double.class);
                        String meta = Double.toString(metaD);
                        metaV.setText(meta);
                        Double muscleD = dataSnapshot.child("muscle").getValue(Double.class);
                        String muscle = Double.toString(muscleD);
                        muscleV.setText(muscle);
                        Double visceralD = dataSnapshot.child("visceralFat").getValue(Double.class);
                        String visceral = Double.toString(visceralD);
                        visceralV.setText(visceral);
                        Double waterD = dataSnapshot.child("water").getValue(Double.class);
                        String water = Double.toString(waterD);
                        waterV.setText(water);
                        String comment = dataSnapshot.child("comment").getValue(String.class);
                        if (comment != null){
                            commentV.setVisibility(View.GONE);
                            commentButton.setText("Show Comment");
                            commentButton.setVisibility(View.VISIBLE);
                        }else if (comment == null){
                            commentV.setVisibility(View.GONE);
                            commentButton.setText("Comment");
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

                Intent intent = new Intent(getApplicationContext(),CoachBodyCommentActivity.class);

                String userId = getIntent().getStringExtra( CoachShowAllBodyActivity.USER_ID);
                String bodyId = getIntent().getStringExtra( ShowAllBodyActivity.BODY_ID);
                intent.putExtra(USER_ID1,userId);
                intent.putExtra(BODY_ID1,bodyId);
                startActivity(intent);
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
                startActivity(new Intent(CoachShowBodyDetailActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, CustomerLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(CoachShowBodyDetailActivity.this, AnalysisActivity.class));
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

