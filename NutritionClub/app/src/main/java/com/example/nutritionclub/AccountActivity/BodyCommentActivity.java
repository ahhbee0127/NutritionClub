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

        import java.util.HashMap;
        import java.util.Map;

public class BodyCommentActivity extends AppCompatActivity {


    public static String BODY_ID1;
    private DatabaseReference mDatabaseBody;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth auth;

    private EditText commentF;
    private TextView commentV;
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

        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userId = firebaseUser.getUid();
        String bodyId = getIntent().getStringExtra( ShowBodyDetailActivity.BODY_ID1);
        mDatabaseBody = FirebaseDatabase.getInstance().getReference("Body Compositions").child(userId);


        mDatabaseBody.child(bodyId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String comment = dataSnapshot.child("comment").getValue(String.class);
                        if (comment != null) {
                            commentV.setText(comment);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

    }
}

