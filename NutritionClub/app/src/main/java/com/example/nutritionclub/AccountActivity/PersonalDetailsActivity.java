package com.example.nutritionclub.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutritionclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;
import java.util.List;

public class PersonalDetailsActivity extends AppCompatActivity {

    private EditText nameF;
    private EditText ageF;
    private EditText heightF;
    private EditText inviterF;
    private EditText contactF;
    private Spinner ncBranchF;
    private UserList userList;

    private Button saveButton;

    private FirebaseAuth auth;

    //private List<User> userList;

    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();

        nameF = (EditText) findViewById(R.id.nameF);
        ageF = (EditText) findViewById(R.id.ageF);
        heightF = (EditText) findViewById(R.id.heightF);
        inviterF = (EditText) findViewById(R.id.inviterF);
        contactF = (EditText) findViewById(R.id.contactF);
        ncBranchF = (Spinner) findViewById(R.id.ncSpinner);

        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
                startActivity(new Intent(PersonalDetailsActivity.this, ShowPersonalActivity.class));
                finish();
            }
        });

    }

    public void onButtonClick(View v) {
        saveUserInfo();
        startActivity(new Intent(PersonalDetailsActivity.this, ShowPersonalActivity.class));
        finish();
    }

    protected void saveUserInfo() {
        String name = nameF.getText().toString().trim();
        String ageS = ageF.getText().toString().trim();
        int age = Integer.parseInt(ageS);
        String heightS = heightF.getText().toString().trim();
        double height = Double.parseDouble(heightS);
        String inviter = inviterF.getText().toString().trim();
        String contact = contactF.getText().toString().trim();
//<<<<<<< HEAD

        String branch = ncBranchF.getSelectedItem().toString().trim();

        FirebaseUser authUser = auth.getCurrentUser();
        User theUser = new User(name,age,height,contact,inviter,branch);

        mDatabaseUser.child(authUser.getUid()).setValue(theUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//=======
//        String branch = ncBranchF.toString().trim();
//
//        final FirebaseUser authUser = auth.getCurrentUser();
//        String userId = authUser.getUid();
//        User theUser = userList.findId(userId);
//
//        theUser.setName(name);
//        theUser.setAge(age);
//        theUser.setHeight(height);
//        theUser.setInviter((inviter));
//        theUser.setNutritionClub(branch);
//        theUser.setContact(contact);
//
//        mDatabaseUser.child(authUser.getUid()).setValue(authUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//>>>>>>> master
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PersonalDetailsActivity.this, "Stored..", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PersonalDetailsActivity.this, "Error..", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}