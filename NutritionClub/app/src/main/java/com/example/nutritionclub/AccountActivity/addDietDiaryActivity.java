package com.example.nutritionclub.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nutritionclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addDietDiaryActivity extends AppCompatActivity {

    private Spinner mealSpinner;
    private ImageButton imageButton;
    private Button uploadButton;
    private int Gallery_intent = 2;
    private DatabaseReference mDatabaseDiet;
    private StorageReference mStorage;
    private FirebaseAuth auth;
    StorageReference filepath;
    private ImageView imageView;
    private Button saveButton;
    private String downloadLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet_diary);

        mealSpinner = (Spinner) findViewById(R.id.mealSpinner);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        imageView = (ImageView) findViewById(R.id.imageView2);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseDiet = FirebaseDatabase.getInstance().getReference().child("Diet Diary");

        auth = FirebaseAuth.getInstance();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeValue();
                startActivity(new Intent(addDietDiaryActivity.this, DietDiaryActivity.class));
                finish();
            }
        });

    }

    private void storeValue(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String todayDate = df.format(c);


        String meal = mealSpinner.getSelectedItem().toString().trim();

        String id = mDatabaseDiet.push().getKey();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        final Diet diet = new Diet(id, downloadLink, meal, todayDate, null);

        mDatabaseDiet.child(userId).child(id).setValue(diet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(addDietDiaryActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(addDietDiaryActivity.this,"Error..",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_intent && resultCode == RESULT_OK){
            Uri uri = data.getData();
            filepath = mStorage.child("Meal").child(uri.getLastPathSegment());
            //imageView.setImageURI(uri);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    downloadLink = downloadUrl.toString().trim();
                    Picasso.with(addDietDiaryActivity.this).load(downloadUrl).fit().centerCrop().into(imageView);
                    Toast.makeText(addDietDiaryActivity.this,"Uploaded",Toast.LENGTH_SHORT);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addDietDiaryActivity.this,"Not Uploaded",Toast.LENGTH_SHORT);
                }
            });
        }
    }
}

































