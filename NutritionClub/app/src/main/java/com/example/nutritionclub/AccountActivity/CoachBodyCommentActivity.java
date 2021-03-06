package com.example.nutritionclub.AccountActivity;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
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

        import java.security.PublicKey;
        import java.util.HashMap;
        import java.util.Map;

public class CoachBodyCommentActivity extends AppCompatActivity{


    public static String BODY_ID1;
    public  static String USER_ID;
    private DatabaseReference mDatabaseBody;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;
    private TextView commentV;

    private EditText commentF;
    private Button submitButton;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_comment);

        commentF = (EditText) findViewById(R.id.commentF);
        submitButton= (Button) findViewById(R.id.submitButton);
        commentV = (TextView) findViewById(R.id.commentV);


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
                            commentV.setVisibility(View.VISIBLE);
                            commentV.setText(comment);
                        }else if (comment == null){
                            commentV.setVisibility(View.GONE);
                            commentF.setVisibility(View.VISIBLE);
                            submitButton.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComment();
            }
        });
    }

    protected void saveComment() {
        final String comment = commentF.getText().toString().trim();

        if(comment.equals("")) {

            Toast.makeText(this, "Please fill in comment before submit.", Toast.LENGTH_SHORT).show();
            return;

        }else {
            String bodyId = getIntent().getStringExtra(CoachShowBodyDetailActivity.BODY_ID1);
            DatabaseReference bodyRef = mDatabaseBody.child(bodyId);

            Map<String, Object> bodyUpdates = new HashMap<>();
            bodyUpdates.put("comment", comment);


            bodyRef.updateChildren(bodyUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CoachBodyCommentActivity.this, "Stored..", Toast.LENGTH_LONG).show();
                        commentV.setText(comment);
                    } else {
                        Toast.makeText(CoachBodyCommentActivity.this, "Error..", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        submitButton.setVisibility(View.GONE);
        commentV.setVisibility(View.VISIBLE);
        commentF.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String bodyId = getIntent().getStringExtra( CoachShowBodyDetailActivity.BODY_ID1);
        String userId = getIntent().getStringExtra( CoachShowBodyDetailActivity.USER_ID1);
        Intent intent;

        switch (id){

            case R.id.edit:
                intent = new Intent(getApplicationContext(),EditCommentActivity.class);

                intent.putExtra(BODY_ID1,bodyId);
                intent.putExtra(USER_ID,userId);
                startActivity(intent);
                break;

            case R.id.delete:

                bodyId = getIntent().getStringExtra( CoachShowBodyDetailActivity.BODY_ID1);

                DatabaseReference bodyRef = mDatabaseBody.child(bodyId);

                Map<String, Object> bodyUpdates = new HashMap<>();
                bodyUpdates.put("comment", "");

                intent = new Intent(getApplicationContext(),CoachShowBodyDetailActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}

