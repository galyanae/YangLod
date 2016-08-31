package com.goodthinking.younglod.user;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.util.Log.*;

public class OneCourseMainActivity extends BaseActivity {
    private FirebaseDatabase root;
    private DatabaseReference newItem;
    private User user;
    private FirebaseAuth mAuth;
    FirebaseUser fUser;
    String UID;
    Course course;
    TextView  courseHeadLine, courseStartdate, coursetime, courseGide,
              courseSynopsys,courseInfo,courseEndDate, maxNoOfParticipetors,
              numberPrticipate, courseCost, courseAduience, courseLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_course_main);
        mAuth = FirebaseAuth.getInstance();
        course = getIntent().getParcelableExtra("oneCourse");


        courseHeadLine = (TextView) findViewById(R.id.courseHeadLine);
        courseHeadLine.setText(course.getCourseHeadLine());
        courseHeadLine.setTextSize(30);

        courseGide = (TextView) findViewById(R.id.courseGide);
        courseGide.setText(course.getCourseGide());

        courseSynopsys = (TextView) findViewById(R.id.courseSynopsys);
        courseSynopsys.setText(course.getCourseSynopsys());

        courseStartdate = (TextView) findViewById(R.id.courseStartdate);
        courseStartdate.setText(course.getCourseStartdate());

        coursetime = (TextView) findViewById(R.id.coursetime);
        coursetime.setText(course.getCoursetime());

        courseInfo = (TextView) findViewById(R.id.courseInfo);
        courseInfo.setText(course.getCourseInfo());

        courseEndDate = (TextView) findViewById(R.id.courseEndDate);
        courseEndDate.setText(course.getCourseEndDate());

        maxNoOfParticipetors = (TextView) findViewById(R.id.maxNoOfParticipetors);
        maxNoOfParticipetors.setText(course.getMaxNoOfParticipetors());

        numberPrticipate = (TextView) findViewById(R.id.numberPrticipate);
        numberPrticipate.setText(course.getNumberPrticipate());

        courseCost = (TextView) findViewById(R.id.courseCost);
        courseCost.setText(course.getCourseCost());

        courseAduience = (TextView) findViewById(R.id.courseAudience);
        courseAduience.setText(course.getCourseCost());

        courseLang = (TextView) findViewById(R.id.courseLang);
        courseLang.setText(course.getCourseLang());

    }
    @Override

    public void onStart() {
        super.onStart();

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();}

//        if (fUser == null) {
//            // Activate Login-tikva login
//            startActivity(new Intent(OneCourseMainActivity.this, SignInActivity.class));
//        }
//        //taking the Uid
//        else UID = fUser.getUid();
//    }
    //sign up for course

    public void registere(View view) {
        Intent intent=new Intent(OneCourseMainActivity.this,CourseRegistrationActivity.class);
        startActivity(intent);

    }
}
