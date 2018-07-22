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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutritionclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalculateFatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_fat);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navEmail = (TextView) findViewById(R.id.navEmailT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                startActivity(new Intent(CalculateFatActivity.this, MainActivity.class));
                break;

            case R.id.nav_calFat:
                startActivity(new Intent(CalculateFatActivity.this, CalculateFatActivity.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onButtonClick(View v) {
        EditText txtInWeight = (EditText)findViewById(R.id.weightText);
        EditText txtInPercent = (EditText)findViewById(R.id.fatPercentText);
        TextView viewResult = (TextView)findViewById(R.id.result);
        TextView viewOpps = (TextView)findViewById(R.id.opps);

        double weight = Double.parseDouble(txtInWeight.getText().toString());
        double percent = Double.parseDouble(txtInPercent.getText().toString());

        double result = ( percent / 100 ) * weight;

        String hihi = txtInPercent.getText().toString().trim();
        mDatabase.child("number").setValue(hihi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CalculateFatActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CalculateFatActivity.this,"Error..",Toast.LENGTH_LONG).show();
                }
            }
        });


        viewResult.setText(Double.toString(result) + " KG");
        viewResult.setVisibility(View.VISIBLE);
        viewOpps.setVisibility(View.VISIBLE);
    }
}
