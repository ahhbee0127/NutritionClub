package com.example.nutritionclub.AccountActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.IntegerRes;
        import android.support.annotation.NonNull;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.design.widget.Snackbar;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CalendarView;
        import android.widget.EditText;
        import android.widget.Spinner;
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

        import java.util.HashMap;
        import java.util.Map;

public class ManageRoleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;
    private TextView nameV;
    private EditText adminCodeF;
    private Spinner roleSpinner;
    private Button doneButton;
    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_role);

        nameV = (TextView) findViewById(R.id.nameV);
        adminCodeF = (EditText) findViewById(R.id.adminCodeV);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        doneButton = (Button) findViewById(R.id.doneButton);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        auth = FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String userId = getIntent().getStringExtra( ShowAllUserActivity.USER_ID);

        mDatabaseUser.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        nameV.setText(name);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageRole();
            }
        });

        hideItem();
    }

    public void manageRole() {

        String adminCode = adminCodeF.getText().toString().trim();
        if (adminCode.equals("")) {
            Toast.makeText(this, "Please fill in Admin Passcode to continue.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!adminCode.equals("123456")) {
            Toast.makeText(this, "Wrong Passcode, please try again.", Toast.LENGTH_SHORT).show();
            return;
        }else if (adminCode.equals("123456")) {

            String role = roleSpinner.getSelectedItem().toString().trim();

            String userId = getIntent().getStringExtra(ShowAllUserActivity.USER_ID);

            DatabaseReference userRef = mDatabaseUser.child(userId);
            Map<String, Object> userUpdates = new HashMap<>();
            userUpdates.put("role", role);

            userRef.updateChildren(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ManageRoleActivity.this, "Role Updated!", Toast.LENGTH_LONG).show();
//                    appController.addUser(userInfo);;
                    } else {
                        Toast.makeText(ManageRoleActivity.this, "Error..", Toast.LENGTH_LONG).show();
                    }
                }
            });

            startActivity(new Intent(ManageRoleActivity.this, ShowAllUserActivity.class));
            finish();
        }
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
                startActivity(new Intent(ManageRoleActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(ManageRoleActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(ManageRoleActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(ManageRoleActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(ManageRoleActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(ManageRoleActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(ManageRoleActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(ManageRoleActivity.this, ShowAllLogActivity.class));
                finish();
                break;

//            case R.id.nav_analysis:
//                startActivity(new Intent(CalculateFatActivity.this, AnalysisActivity.class));
//                finish();
//                break;

            case R.id.nav_info:
                startActivity(new Intent(ManageRoleActivity.this, InfoCornerActivity.class));
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
