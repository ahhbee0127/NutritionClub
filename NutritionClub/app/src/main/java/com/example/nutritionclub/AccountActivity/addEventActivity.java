package com.example.nutritionclub.AccountActivity;

        import android.app.DatePickerDialog;
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
        import android.widget.CalendarView;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.TimePicker;
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
        import java.util.Locale;

public class addEventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabaseEvent;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;

    private EditText eDateF;
    private EditText eToF;
    private EditText eFromF;
    private EditText eDescriptionf;
    private EditText eNameF;
    private Button saveButton;
    private Button dateDoneButton;
    private Button timeDoneButton1;
    private CalendarView eCalendarView;
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    private Button timeDoneButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eDateF = (EditText) findViewById(R.id.eDateF);
        eFromF = (EditText) findViewById(R.id.eFromF);
        eToF = (EditText) findViewById(R.id.eToF);
        eNameF = (EditText) findViewById(R.id.eNameF);
        eDescriptionf = (EditText) findViewById(R.id.eDescriptionF);
        saveButton = (Button) findViewById(R.id.saveButton);
        eCalendarView = (CalendarView) findViewById(R.id.eCalendarView);
        dateDoneButton = (Button) findViewById(R.id.dateDoneButton);
        timeDoneButton1 = (Button) findViewById(R.id.timeDoneButton1);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timeDoneButton2 = (Button) findViewById(R.id.timeDoneButton2);
        timePicker2 = (TimePicker) findViewById(R.id.timePicker2);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        final Calendar myCalendar = Calendar.getInstance();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        mDatabaseEvent = FirebaseDatabase.getInstance().getReference("Events");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hideItem();

//        eDateF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                eCalendarView.setVisibility(View.VISIBLE);
//                dateDoneButton.setVisibility(View.VISIBLE);
//            }
//        });
//
//        dateDoneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                eDateF.setVisibility(View.GONE);
//                dateDoneButton.setVisibility(View.GONE);
//            }
//        });
//
//        eCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//                String date = (i2 + 1) + "-" + i1 + "-" + i;
//                eDateF.setText(date);
//            }
//        });
//
//
//        eFromF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePicker1.setVisibility(View.VISIBLE);
//                timeDoneButton1.setVisibility(View.VISIBLE);
//            }
//        });
//
//        timeDoneButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePicker1.setVisibility(View.GONE);
//                timeDoneButton1.setVisibility(View.GONE);
//            }
//        });
//
//        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
//                String fromTime = (i1 + i + "");
//                eDateF.setText(fromTime);
//            }
//        });
//
//        eToF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePicker2.setVisibility(View.VISIBLE);
//                timeDoneButton2.setVisibility(View.VISIBLE);
//            }
//        });
//
//        timeDoneButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePicker2.setVisibility(View.GONE);
//                timeDoneButton2.setVisibility(View.GONE);
//            }
//        });
//
//        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
//                String toTime = (i1 + i + "");
//                eToF.setText(toTime);
//            }
//        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
                startActivity(new Intent(addEventActivity.this, ActivityBoardActivity.class));
                finish();
            }
        });
    }

    protected void saveEvent() {
        String date = eDateF.getText().toString().trim();
        String description = eDescriptionf.getText().toString().trim();
        String time = eFromF.getText().toString().trim();
        String eventName = eNameF.getText().toString().trim();


        String id = mDatabaseEvent.push().getKey();
//        FirebaseUser user = auth.getCurrentUser();
//        String userId = user.getUid();

        final Event event = new Event(id,date,time,eventName,description);

        mDatabaseEvent.child(id).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(addEventActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(addEventActivity.this,"Error..",Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(addEventActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.nav_me:
                startActivity(new Intent(addEventActivity.this, ShowPersonalActivity.class));
                finish();
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(addEventActivity.this, CalculateFatActivity.class));
                finish();
                break;

            case R.id.nav_showAllUser:
                startActivity(new Intent(addEventActivity.this, ShowAllUserActivity.class));
                finish();
                break;

            case R.id.nav_bodyComposition:
                startActivity(new Intent(addEventActivity.this, ShowAllBodyActivity.class));
                finish();
                break;

            case R.id.nav_diet:
                startActivity(new Intent(addEventActivity.this, DietDiaryActivity.class));
                finish();
                break;

            case R.id.nav_activityBoard:
                startActivity(new Intent(addEventActivity.this, ActivityBoardActivity.class));
                finish();
                break;

            case R.id.nav_customerLog:
                startActivity(new Intent(addEventActivity.this, ShowAllLogActivity.class));
                finish();
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(addEventActivity.this, AnalysisActivity.class));
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
