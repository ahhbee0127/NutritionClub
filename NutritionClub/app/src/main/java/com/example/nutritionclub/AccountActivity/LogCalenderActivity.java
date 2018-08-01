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

import com.example.nutritionclub.R;

public class LogCalenderActivity extends AppCompatActivity {

    private String date;
    public static final String DATE = "date";
    private CalendarView calendarView;
    private Button nextButton;
    private TextView myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calender);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        nextButton = (Button) findViewById(R.id.nextButton);
        myDate = (TextView) findViewById(R.id.myDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = (i2 + 1) + "-" + i1 + "-" + i;
                myDate.setText(date);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Checkin checkin = new Checkin(date);
                Intent intent = new Intent(getApplicationContext(),CustomerLogActivity.class);

                intent.putExtra(DATE,checkin.getDate());

                startActivity(intent);
            }
        });
    }
}
