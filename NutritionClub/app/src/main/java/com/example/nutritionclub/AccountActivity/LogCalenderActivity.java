package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutritionclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LogCalenderActivity extends AppCompatActivity {

    private String date;
    public static final String DATE = "date";
    public static String CHECKINDATEID = "checkinDateId";
    private CalendarView calendarView;
    private Button nextButton;
    private TextView myDate;
    private DatabaseReference mDatabaseCheckinDate;
    CheckinDate checkinDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calender);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        nextButton = (Button) findViewById(R.id.nextButton);
        myDate = (TextView) findViewById(R.id.myDate);

        mDatabaseCheckinDate = FirebaseDatabase.getInstance().getReference("Log Date");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = (i1 + 1) + "-" + i2 + "-" + i;
                myDate.setText(date);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                storeValue();

                //CheckinDate checkinDate = new CheckinDate();

                Intent intent = new Intent(getApplicationContext(),CustomerLogActivity.class);

                String mdate = myDate.getText().toString().trim();
                //intent.putExtra(DATE,checkinDate.getDate());
                intent.putExtra(DATE,mdate);
                //intent.putExtra(CHECKINDATEID,checkinDate.getCheckinDateId());

                startActivity(intent);
                finish();
            }
        });
    }

    public void storeValue(){

        String mdate = myDate.getText().toString().trim();

        //CHECKINDATEID = mdate;

        checkinDate = new CheckinDate(mdate);

        mDatabaseCheckinDate.child(mdate).setValue(checkinDate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogCalenderActivity.this,"Proceeding..",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LogCalenderActivity.this,"Error..",Toast.LENGTH_LONG).show();
                }
            }
        });
//        Map<String, Object> checkinDateUpdates = new HashMap<>();
//        checkinDateUpdates.put("name", name);
//        checkinDateUpdates.put("age", age);
//        userUpdates.put("contact", contact);
//        userUpdates.put("height", height);
//        userUpdates.put("inviter", inviter);
//        userUpdates.put("nutritionClub", branch);
//
//
//        userRef.updateChildren(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(PersonalDetailsActivity.this,"Stored..",Toast.LENGTH_LONG).show();
////                    appController.addUser(userInfo);;
//                }else{
//                    Toast.makeText(PersonalDetailsActivity.this,"Error..",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
}
