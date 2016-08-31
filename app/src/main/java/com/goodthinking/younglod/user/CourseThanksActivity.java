package com.goodthinking.younglod.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CourseThanksActivity extends AppCompatActivity {

    private DatabaseReference root;
    private FirebaseAuth mAuth;
    private String keyUserId;
    private String keyCourse;

    private TextView CourseThanksnoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_thanks);

        Intent intent = getIntent();
        keyCourse = intent.getStringExtra("courseID");

        root = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        keyUserId = mAuth.getCurrentUser().getUid();

       CourseThanksnoteText = (TextView) findViewById(R.id.CourseThanksnote);

        checkRegistration();
    }

    private void checkRegistration() {
        Query qrefUserRegister = root.child("Tables").child("Courses").child(keyCourse).child("Applicants").child(keyUserId);
        qrefUserRegister.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null){
                    CourseThanksnoteText.setText(R.string.Thanks_cancel_note);
                    //  EventThanksnoteText.setText("You have canceled your registration");
                    //  Toast.makeText(EventInformationActivity_Firebase.this, "No record found",
                    //          Toast.LENGTH_SHORT).show();
                }
                else {
                   CourseThanksnoteText.setText(R.string.Thanks_note_course);
                    // EventThanksnoteText.setText("Thank you for your interest in the event, Our representative will contact you as soon as possible.  " +
                    //   "This registration does not constitute approval of your participation");
                    // Toast.makeText(EventInformationActivity_Firebase.this, snapshot.getValue().toString(),
                    //         Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Download failed"+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void returntomain(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }

    public void GoToMyEventsFromThanksB(View view) {
        startActivity(new Intent(getApplicationContext(),MyCoursesActivity.class));
    }


}
