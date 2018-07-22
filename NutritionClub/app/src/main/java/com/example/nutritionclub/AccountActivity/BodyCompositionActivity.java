package com.example.nutritionclub.AccountActivity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nutritionclub.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BodyCompositionActivity extends AppCompatActivity {

    private Button button;
    private TextView nameText;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_composition);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        button = (Button)findViewById(R.id.button);
        nameText = (TextView)findViewById(R.id.nameText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString().trim();

                mDatabase.child("Name").setValue(name);
            }
        });

    }
}
