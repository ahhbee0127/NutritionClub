package com.example.nutritionclub.AccountActivity;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nutritionclub.R;

public class CalculateFatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_fat);
    }

    public void onButtonClick(View v) {
        EditText txtInWeight = (EditText)findViewById(R.id.weightText);
        EditText txtInPercent = (EditText)findViewById(R.id.fatPercentText);
        TextView viewResult = (TextView)findViewById(R.id.result);
        TextView viewOpps = (TextView)findViewById(R.id.opps);

        double weight = Double.parseDouble(txtInWeight.getText().toString());
        double percent = Double.parseDouble(txtInPercent.getText().toString());

        double result = ( percent / 100 ) * weight;
        viewResult.setText(Double.toString(result) + " KG");
        viewResult.setVisibility(View.VISIBLE);
        viewOpps.setVisibility(View.VISIBLE);
    }
}
