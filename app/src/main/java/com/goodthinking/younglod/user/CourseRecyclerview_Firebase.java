package com.goodthinking.younglod.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goodthinking.younglod.user.model.Course;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Owner on 03/09/2016.
 */
public class CourseRecyclerview_Firebase extends AppCompatActivity {
    private RecyclerView CourseRecyclerView;
    private CourseRecyclerAdapter CourseRecyclerAdapter;
    private FirebaseAuth auth;
    private DatabaseReference Coursedatabase, MyCoursedatabase;
    private String flag_to_myCourses;
    boolean isManager = false;
    String role = "user";
    private Query queryRef;

    private DatabaseReference Userdatabase;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_recyclerview_firebase);
        CourseRecyclerView = (RecyclerView) findViewById(R.id.CourseRecyclerview);


        try {
            role = getIntent().getExtras().getString("Role");
        } catch (Exception e) {
            role = "user";
        }
        if (role.equals("manager")) {isManager = true;
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);}

        System.out.println("Am I a manager? " + isManager);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        CourseRecyclerView.setLayoutManager(linearLayoutManager);
        CourseRecyclerAdapter = new CourseRecyclerAdapter(getApplicationContext());
        CourseRecyclerView.setAdapter(CourseRecyclerAdapter);
        CourseRecyclerAdapter.notifyDataSetChanged();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshallCourses();

    }

    private void RefreshallCourses() {

        Coursedatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refCourses = Coursedatabase.child("Tables").child("Courses");
        queryRef = refCourses.orderByChild("statusIsValidDate").startAt("1");
        //  Coursedatabase.child("Amuta").child("Courses").addListenerForSingleValueCourse(new ValueCourseListener() {
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CourseToArrayData(dataSnapshot);
                CourseArrayData.getInstance().getCourses().clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Course course = data.getValue(Course.class);
                    course.setKey(data.getKey());
                    CourseArrayData.getInstance().getCourses().add(course);
                }
                CourseRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void addNewCourse(View view) {
        Intent intent = new Intent(CourseRecyclerview_Firebase.this, CourseAddNew_Firebase.class);
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CourseRecyclerview_Firebase Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.goodthinking.younglod.user/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CourseRecyclerview_Firebase Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.goodthinking.younglod.user/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
