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

public class CoachShowAllBodyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BODY_ID = "bodyId";
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static double VISCERAL_FAT;
    public static double FAT_PERCENT;
    public static  String USER_ID = "userId";
    private DatabaseReference mDatabaseBody;
    private DatabaseReference mDatabaseUsers;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private FirebaseAuth auth;
    private Button addRecordButton;

    ListView listViewBody;
    List<BodyComposition> bodyCompositionList;
    //ArrayAdapter<User> userAdapter;
    BodyComposition bodyComposition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_body);

        addRecordButton = (Button) findViewById(R.id.addRecordButton);

        bodyComposition = new BodyComposition();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        //Later when comes to Admin View different.
//        FirebaseUser user = auth.getCurrentUser();
//        final String userId = user.getUid();

        USER_ID = getIntent().getStringExtra( ShowAllUserActivity.USER_ID);

        mDatabaseBody = FirebaseDatabase.getInstance().getReference("Body Compositions").child(USER_ID);

        listViewBody = (ListView) findViewById(R.id.bodyCompositionListView);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bodyCompositionList = new ArrayList<>();

//        addRecordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(CoachShowAllBodyActivity.this, BodyCompositionActivity.class));
//            }
//        });

        hideItem();

        listViewBody.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BodyComposition bodyComposition = bodyCompositionList.get(i);

                //String age = getIntent().getStringExtra( ShowDetailActivity.AGE);
                String gender = getIntent().getStringExtra( ShowDetailActivity.GENDER);

                Intent intent = new Intent(getApplicationContext(),CoachShowBodyDetailActivity.class);

                intent.putExtra(USER_ID,bodyComposition.getUserId());
                intent.putExtra(BODY_ID,bodyComposition.getBodyId());
                //intent.putExtra(AGE,age);
                intent.putExtra(GENDER,gender);
                //intent.putExtra(VISCERAL_FAT,bodyComposition.getVisceralFat());
                //intent.putExtra(FAT_PERCENT,bodyComposition.getFatPercent());

                VISCERAL_FAT = bodyComposition.getVisceralFat();
                FAT_PERCENT = bodyComposition.getFatPercent();

                startActivity(intent);
            }
        });
    }

    public static double getVisceralFat(){
        return VISCERAL_FAT;
    }

    public static double getFatPercent(){
        return FAT_PERCENT;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseBody.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bodyCompositionList.clear();

                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    BodyComposition bodyComposition = userSnapshot.getValue(BodyComposition.class);

                    bodyCompositionList.add(bodyComposition);
                }

                BodyCompositionList adapter = new BodyCompositionList(CoachShowAllBodyActivity.this,bodyCompositionList);
                listViewBody.setAdapter(adapter);
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
                startActivity(new Intent(CoachShowAllBodyActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(CoachShowAllBodyActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CoachShowAllBodyActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(CoachShowAllBodyActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(CoachShowAllBodyActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(CoachShowAllBodyActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(CoachShowAllBodyActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(CoachShowAllBodyActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(CoachShowAllBodyActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(CoachShowAllBodyActivity.this, InfoCornerActivity.class));
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
                            addRecordButton.setVisibility(View.GONE);
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

