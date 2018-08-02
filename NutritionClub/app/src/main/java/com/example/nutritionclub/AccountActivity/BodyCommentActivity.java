package com.example.nutritionclub.AccountActivity;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.view.menu.SubMenuBuilder;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
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
        import java.util.HashMap;
        import java.util.Map;

public class BodyCommentActivity extends AppCompatActivity{


    private DatabaseReference mDatabaseBody;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;

    private EditText commentF;
    private Button submitButton;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_comment);

        commentF = (EditText) findViewById(R.id.commentF);
        submitButton= (Button) findViewById(R.id.submitButton);


        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

        String userId = getIntent().getStringExtra( CoachShowBodyDetailActivity.USER_ID1);
        String bodyId = getIntent().getStringExtra( CoachShowBodyDetailActivity.BODY_ID1);
        mDatabaseBody = FirebaseDatabase.getInstance().getReference("Body Compositions").child(userId);


        mDatabaseBody.child(bodyId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String comment = dataSnapshot.child("comment").getValue(String.class);
                        if (comment != null) {
                            commentF.setText(comment);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        hideItem();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComment();
                submitButton.setVisibility(View.GONE);
            }
        });
    }

    protected void saveComment() {
        String comment = commentF.getText().toString().trim();
        String bodyId = getIntent().getStringExtra( CoachShowBodyDetailActivity.BODY_ID1);
        DatabaseReference bodyRef = mDatabaseBody.child(bodyId);

        Map<String, Object> bodyUpdates = new HashMap<>();
        bodyUpdates.put("comment", comment);



        bodyRef.updateChildren(bodyUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(BodyCommentActivity.this,"Stored..",Toast.LENGTH_LONG).show();
//                    appController.addUser(userInfo);;
                }else{
                    Toast.makeText(BodyCommentActivity.this,"Error..",Toast.LENGTH_LONG).show();
                }
            }
        });
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
                        String comment = dataSnapshot.child("comment").getValue(String.class);

                        if(role.equals("coach")){
                            if(comment == null){
                                submitButton.setVisibility(View.VISIBLE);
                            }

                        }else if(role.equals("client")){

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUser:onCancelled", databaseError.toException());
                    }
                });
    }
}

