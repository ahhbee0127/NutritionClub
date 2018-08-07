package com.example.nutritionclub.AccountActivity;

        import android.content.Intent;
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

public class EditBodyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabaseBody;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;

    private EditText weightF;
    private EditText waterF;
    private EditText fatF;
    private EditText viceralF;
    private EditText boneMassF;
    private EditText metaAgeF;
    private EditText muscleF;
    private Button saveButton;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_body);

        weightF = (EditText) findViewById(R.id.eDateF);
        waterF = (EditText) findViewById(R.id.eNameF);
        fatF = (EditText) findViewById(R.id.eFromF);
        viceralF = (EditText) findViewById(R.id.vfatF);
        boneMassF = (EditText) findViewById(R.id.boneMassF);
        metaAgeF = (EditText) findViewById(R.id.metaAgeF);
        muscleF = (EditText) findViewById(R.id.muscleF);
        saveButton = (Button) findViewById(R.id.saveButton);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        FirebaseUser user= auth.getCurrentUser();
        String userId = user.getUid();
        mDatabaseBody = FirebaseDatabase.getInstance().getReference("Body Compositions").child(userId);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String bodyId = getIntent().getStringExtra( ShowBodyDetailActivity.BODY_ID1);

        mDatabaseBody.child(bodyId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double weightD = dataSnapshot.child("weight").getValue(Double.class);
                        String weight = Double.toString(weightD);
                        weightF.setText(weight);
                        Double fatPercentD = dataSnapshot.child("fatPercent").getValue(Double.class);
                        String fatPercent = Double.toString(fatPercentD);
                        fatF.setText(fatPercent);
                        Double boneMassD = dataSnapshot.child("boneMass").getValue(Double.class);
                        String boneMass = Double.toString(boneMassD);
                        boneMassF.setText(boneMass);
                        int metaD = dataSnapshot.child("metabolicAge").getValue(Integer.class);
                        String meta = Integer.toString(metaD);
                        metaAgeF.setText(meta);
                        Double muscleD = dataSnapshot.child("muscle").getValue(Double.class);
                        String muscle = Double.toString(muscleD);
                        muscleF.setText(muscle);
                        int visceralD = dataSnapshot.child("visceralFat").getValue(Integer.class);
                        String visceral = Integer.toString(visceralD);
                        //String visceral = String.format("%.0f",visceralD);
                        viceralF.setText(visceral);
                        Double waterD = dataSnapshot.child("water").getValue(Double.class);
                        String water = Double.toString(waterD);
                        waterF.setText(water);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });


    hideItem();

        saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveBodyComposition();
            startActivity(new Intent(EditBodyActivity.this, ShowAllBodyActivity.class));
            finish();
        }
    });
}

    protected void saveBodyComposition() {
        String waterS = waterF.getText().toString().trim();
        double water = Double.parseDouble(waterS);
        String weightS = weightF.getText().toString().trim();
        double weight = Double.parseDouble(weightS);
        String fatPercentS = fatF.getText().toString().trim();
        double fatPercent = Double.parseDouble(fatPercentS);
        String visceralFatS = viceralF.getText().toString().trim();
        int visceralFat = Integer.parseInt(visceralFatS);
        String boneMassS = boneMassF.getText().toString().trim();
        double boneMass = Double.parseDouble(boneMassS);
        String metaS = metaAgeF.getText().toString().trim();
        int metaAge = Integer.parseInt(metaS);
        String muscleS = muscleF.getText().toString().trim();
        double muscle = Double.parseDouble(muscleS);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String todayDate = df.format(c);

        double fatKg1 = (fatPercent/100) * weight;
        String fatKgS = String.format("%.2f", fatKg1);
        double fatKg = Double.parseDouble(fatKgS);

        double bmi = 124;

        String id = getIntent().getStringExtra( ShowBodyDetailActivity.BODY_ID1);
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        final BodyComposition userBody = new BodyComposition(null,userId,id,todayDate,water,weight,fatPercent,visceralFat,boneMass,metaAge,muscle,fatKg,bmi);

        mDatabaseBody.child(id).setValue(userBody).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditBodyActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(EditBodyActivity.this,"Error..",Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(EditBodyActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(EditBodyActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(EditBodyActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(EditBodyActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(EditBodyActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(EditBodyActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(EditBodyActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(EditBodyActivity.this, ShowAllLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(EditBodyActivity.this, AnalysisActivity.class));
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
