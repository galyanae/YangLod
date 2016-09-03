package com.goodthinking.younglod.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.Course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Owner on 03/09/2016.
 */
public class MyCourse_Activity extends AppCompatActivity {
    private RecyclerView CourseRecyclerView;
    private CourseRecyclerAdapter CourseRecyclerAdapter;
    private FirebaseAuth auth;
    private DatabaseReference Coursedatabase,  MyCoursedatabase ;
    ArrayList<String> filter = new ArrayList<>();

    private Boolean CourseIsNotValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_recyclerview_firebase);
        CourseRecyclerView = (RecyclerView) findViewById(R.id.CourseRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        CourseRecyclerView.setLayoutManager(linearLayoutManager);
        CourseRecyclerAdapter = new CourseRecyclerAdapter(getApplicationContext());
        CourseRecyclerView.setAdapter(CourseRecyclerAdapter);
        CourseRecyclerAdapter.notifyDataSetChanged();

        CourseRecyclerAdapter.notifyDataSetChanged();
        CourseArraydata.getInstance().getCourses().clear();
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
                    //System.out.println("MyCourse" + key);
                    filter.add(key);
                }
                //CourseArraydata.getInstance().getCourses().clear();
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
                            CourseIsNotValid = course.getCourseIsNotValid();
                            if (CourseIsNotValid == false){
                                CourseArraydata.getInstance().getCourses().add(course);}
                            CourseRecyclerAdapter.notifyDataSetChanged();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

//
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//
    }

    @Override
    protected void onResume() {
        super.onResume();
        //EventArraydata.getInstance().getEvents().clear();
    }


}

