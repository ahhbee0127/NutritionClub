package com.example.nutritionclub.AccountActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.nutritionclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowPersonalActivity extends AppCompatActivity {

    private TextView nameV;
    private TextView heightV;
    private TextView ageV;
    private TextView contactV;
    private TextView inviterV;
    private TextView branchV;

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_personal);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        nameV = (TextView) findViewById(R.id.nameV);
        contactV = (TextView) findViewById(R.id.contactV);
        ageV = (TextView) findViewById(R.id.ageV);
        inviterV = (TextView) findViewById(R.id.inviterV);
        heightV = (TextView) findViewById(R.id.heightV);
        branchV = (TextView) findViewById(R.id.ncBranchV);

        FirebaseUser authUser = auth.getCurrentUser();
        String userId = authUser.getUid();


        mDatabase.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        nameV.setText(name);
                        double heightD = dataSnapshot.child("height").getValue(Double.class);
                        String height = Double.toString(heightD);
                        heightV.setText(height);
                        int ageI = dataSnapshot.child("age").getValue(Integer.class);
                        String age = Double.toString(ageI);
                        ageV.setText(age);
                        String contact = dataSnapshot.child("contact").getValue(String.class);
                        contactV.setText(contact);
                        String inviter = dataSnapshot.child("inviter").getValue(String.class);
                        inviterV.setText(inviter);
                        String branch = dataSnapshot.child("nutritionClub").getValue(String.class);
                        branchV.setText(branch);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
}
