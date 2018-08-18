package com.example.nutritionclub.AccountActivity;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.provider.ContactsContract;
        import android.support.annotation.IdRes;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
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
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Map;

public class EditDietActivity extends AppCompatActivity {

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
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet_diary);

        mealSpinner = (Spinner) findViewById(R.id.mealSpinner);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        imageView = (ImageView) findViewById(R.id.imageView2);

        auth = FirebaseAuth.getInstance();
        FirebaseUser authUser = auth.getCurrentUser();
        final String currentUserId = authUser.getUid();

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseDiet = FirebaseDatabase.getInstance().getReference("Diet Diary").child(currentUserId);


        String dietId = getIntent().getStringExtra(ShowDietActivity.DIET_ID);

        mDatabaseDiet.child(dietId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int pos;
                        String meal = dataSnapshot.child("meal").getValue(String.class);

                        if(meal.equals("Breakfast")){
                            pos = 0;
                        }else if(meal.equals("Snack")){
                            pos = 1;
                        }else if(meal.equals("Lunch")){
                            pos = 2;
                        }else if(meal.equals("Tea Break")){
                            pos = 3;
                        }else if(meal.equals("Dinner")){
                            pos = 4;
                        }else{
                            pos = 5;
                        }
                        mealSpinner.setSelection(pos);

                        String uri = dataSnapshot.child("image").getValue(String.class);
                        Picasso.with(EditDietActivity.this).load(uri).fit().centerCrop().into(imageView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });



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
            }
        });

    }

    private void storeValue(){

        String meal = mealSpinner.getSelectedItem().toString().trim();

        if (imageView.getDrawable() == null){
            Toast.makeText(this, "Please upload at least one photo before save.", Toast.LENGTH_SHORT).show();
            return;
        }else{


            String dietId = getIntent().getStringExtra(ShowDietActivity.DIET_ID);

            DatabaseReference dietRef = mDatabaseDiet.child(dietId);
            Map<String, Object> dietUpdates = new HashMap<>();
            dietUpdates.put("image", downloadLink);
            dietUpdates.put("meal", meal);



            dietRef.updateChildren(dietUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditDietActivity.this, "Stored..", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditDietActivity.this, "Error..", Toast.LENGTH_LONG).show();
                    }
                }
            });

            startActivity(new Intent(EditDietActivity.this, DietDiaryActivity.class));
            finish();
        }
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
                    Picasso.with(EditDietActivity.this).load(downloadUrl).fit().centerCrop().into(imageView);
                    Toast.makeText(EditDietActivity.this,"Uploaded",Toast.LENGTH_SHORT);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditDietActivity.this,"Not Uploaded",Toast.LENGTH_SHORT);
                }
            });
        }
    }
}

































