package com.goodthinking.youngloduser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goodthinking.youngloduser.user.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EventThanksActivity extends AppCompatActivity {

    private DatabaseReference root;
    private FirebaseAuth mAuth;
    private String keyUserId;
    private String keyEvent;

    private TextView EventThanksnoteText;
    String role;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_thanks);

        Intent intent = getIntent();
        keyEvent = intent.getStringExtra("eventID");
        role = intent.getStringExtra("Role");
        if (role == null) role = "user";
        tableName = intent.getStringExtra("TableName");

        root = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        keyUserId = mAuth.getCurrentUser().getUid();

        EventThanksnoteText = (TextView) findViewById(R.id.EventThanksnote);

        checkRegistration();
    }

    private void checkRegistration() {
        Query qrefUserRegister = root.child("Tables").child(tableName).child(keyEvent).child("Applicants").child(keyUserId);
        qrefUserRegister.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null) {
                    EventThanksnoteText.setText(R.string.Thanks_cancel_note);
                    //  EventThanksnoteText.setText("You have canceled your registration");
                    //  Toast.makeText(EventInformationActivity_Firebase.this, "No record found",
                    //          Toast.LENGTH_SHORT).show();
                } else {
                    EventThanksnoteText.setText(R.string.Thanks_note);
                    // EventThanksnoteText.setText("Thank you for your interest in the event, Our representative will contact you as soon as possible.  " +
                    //   "This registration does not constitute approval of your participation");
                    // Toast.makeText(EventInformationActivity_Firebase.this, snapshot.getValue().toString(),
                    //         Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void returntomain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Role",role);
        intent.putExtra("TableName",tableName);
        startActivity(intent);
        finish();

    }

    public void GoToMyEventsFromThanksB(View view) {
        Intent intent = new Intent(getApplicationContext(), MyEvent_Activity.class);
        intent.putExtra("Role",role);
        intent.putExtra("TableName",tableName);
        startActivity(intent);
    }
}
