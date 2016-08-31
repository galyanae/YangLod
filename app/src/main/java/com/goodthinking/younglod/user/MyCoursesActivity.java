package com.goodthinking.younglod.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCoursesActivity extends AppCompatActivity {

    private RecyclerView CourseRecyclerView;
    private MyAdapter MyAdapter;
    private FirebaseAuth auth;
    private DatabaseReference Coursedatabase,  MyCoursedatabase ;
    ArrayList<String> filter = new ArrayList<>();
    Course course;

    private Boolean CourseIsNotValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        CourseRecyclerView = (RecyclerView) findViewById(R.id.CourseRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        CourseRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter = new MyAdapter(getApplicationContext());
        CourseRecyclerView.setAdapter(MyAdapter);
        MyAdapter.notifyDataSetChanged();

        MyAdapter.notifyDataSetChanged();
        CourseArrayData.getInstance().getCourses().clear();
        RefreshOnlyMyCourses();
    }


    private void RefreshOnlyMyCourses() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UserID = user.getUid();
        //System.out.println(UserID);

        Coursedatabase = FirebaseDatabase.getInstance().getReference();
        MyCoursedatabase = Coursedatabase.child("users").child(UserID).child("MyCourses");
        MyCoursedatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filter.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey();
                    //System.out.println("MyEvent" + key);
                    filter.add(key);
                }
                //EventArraydata.getInstance().getEvents().clear();
                for (int i = 0; i < filter.size(); i++) {
                    final String Coursekey = filter.get(i);
                    System.out.println("key is" + Coursekey);
                    Coursedatabase = FirebaseDatabase.getInstance().getReference();
                    Coursedatabase.child("Tables").child("Courses").child(Coursekey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Course course = dataSnapshot.getValue(Course.class);
                            course.setKey(dataSnapshot.getKey());
                            //String CheckEventKey = event.getKey();
                            //System.out.println("CheckEventkey" + CheckEventKey);

                                CourseArrayData.getInstance().getCourses().add(course);}



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });}}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}}
