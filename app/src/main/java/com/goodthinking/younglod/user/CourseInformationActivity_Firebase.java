package com.goodthinking.younglod.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 03/09/2016.
 */
public class CourseInformationActivity_Firebase extends AppCompatActivity {
    private static final String IMAGES_BUCKET ="gs://hadashot-9bbf1.appspot.com";
    private TextView ViewCourseHeadline, ViewCoursedate, ViewCoursetime,
            ViewCourseSynopsys, ViewCourseInfo, ViewCourseParticipatorsno, ViewCourseHostName, RegistrationIsClosed;
    private DatabaseReference Coursedatabase,  MyCoursedatabase ;
    private int position;
    Button Register;
    String key;
    ImageView imageView;
    private DatabaseReference root;
    private FirebaseAuth mAuth;
    private String keyUserId;
    private Boolean CourseIsClosed;
    private FirebaseStorage storage;
    String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information_activity_firebase);

        root = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        Register=(Button)findViewById(R.id.fbviewregtocoursetbtn);
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            key = intent.getStringExtra("Coursekey");
            position = intent.getIntExtra("position", 0);
            //Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();

            ViewCourseHeadline = (TextView) findViewById(R.id.fbviewCourseHeadlineview);
            ViewCoursedate = (TextView) findViewById(R.id.fbviewCoursedateview);
            ViewCoursetime = (TextView) findViewById(R.id.fbviewCoursetimeview);
            ViewCourseSynopsys = (TextView) findViewById(R.id.fbviewCourseSynopsysview);
            ViewCourseInfo = (TextView) findViewById(R.id.fbviewCourseInfoview);
            ViewCourseParticipatorsno = (TextView) findViewById(R.id.fbviewCourseParticipatorsno);
            ViewCourseHostName = (TextView) findViewById(R.id.fbviewCoursehostname);
            RegistrationIsClosed = (TextView) findViewById(R.id.fbRegistrationIsClosed);
            imageView= (ImageView) findViewById(R.id.imageView2);


//             Course course = (Course) intent.getSerializableExtra("Course");
//            if (course != null) {
//
//
//            ViewEventHeadline.setText(event.getEventName());
//            ViewEventdate.setText(event.getEventDate());
//            ViewEventtime.setText(event.getEventTime());
//            ViewEventSynopsys.setText(event.getEventSynopsys());
//            ViewEventInfo.setText(event.getEventInformation());
//            ViewEventParticipatorsno.setText("" +event.getEventParticipatorsno());
//            ViewEventHostName.setText(event.getEventHost());
//             }

            Course course = CourseArrayData.getInstance().getCourses().get(position);

            ViewCourseHeadline.setText(CourseArrayData.getInstance().getCourses().get(position).getCourseName());
            ViewCoursedate.setText(CourseArrayData.getInstance().getCourses().get(position).getCourseDate());
            ViewCoursetime.setText(CourseArrayData.getInstance().getCourses().get(position).getCourseTime());
            ViewCourseSynopsys.setText(CourseArrayData.getInstance().getCourses().get(position).getCourseSynopsys());
            ViewCourseInfo.setText(CourseArrayData.getInstance().getCourses().get(position).getCourseInformation());
            ViewCourseParticipatorsno.setText(String.valueOf(CourseArrayData.getInstance().getCourses().get(position).getCourseParticipatorsno()));
            ViewCourseHostName.setText(CourseArrayData.getInstance().getCourses().get(position).getCourseHost());
            CourseIsClosed = CourseArrayData.getInstance().getCourses().get(position).getCourseIsClosed();


            StorageReference storageRef=storage.getReferenceFromUrl(IMAGES_BUCKET);


            if (course.getImage() != null && course.getImage().length() > 0) {

                String[] parts = course.getImage().split("/");
                storageRef.child("/images/" + parts[parts.length - 1]).getBytes(1024*1024*5).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bits) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bits, 0, bits.length);
                        imageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }



            if (CourseIsClosed == true){
                Register.setEnabled(false);
                RegistrationIsClosed.setVisibility(View.VISIBLE);
            }
        }

        checkRegistration();
    }

    private void checkRegistration() {
        if (mAuth.getCurrentUser() != null) {
            keyUserId = mAuth.getCurrentUser().getUid();
            Query qrefUserRegister = root.child("Tables").child("Courses").child(key).child("Applicants").child(keyUserId);
            qrefUserRegister.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot == null || snapshot.getValue() == null) {
                        Register.setText(R.string.register_course_btn);
                        //  Toast.makeText(CourseInformationActivity_Firebase.this, "No record found",
                        //          Toast.LENGTH_SHORT).show();
                    } else {
                        Register.setText(R.string.update_register_course_btn);
                        // Toast.makeText(CourseInformationActivity_Firebase.this, snapshot.getValue().toString(),
                        //         Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Register.setText(R.string.register_course_btn);
        }
    }
    public void EditCoursebtn(View view) {
        Intent intent=new Intent(getApplicationContext(),CourseAddNew_Firebase.class);
        intent_putExtra(intent);

    }

    public void CancelAddCoursebtn(View view) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("Role", role);
        startActivity(intent);
        finish();
    }

    public void RegisterCoursebtn(View view) {
        Intent intent = new Intent(getApplicationContext(), CourseRegistrationActivity.class);
        intent_putExtra(intent);
        //  finish();
    }
    public void Listofuserstbtn(View view) {
        Intent intent=new Intent(getApplicationContext(),Applicant_list.class);
        intent_putExtra(intent);
    }


    private void intent_putExtra(Intent intent) {
        intent.putExtra("Coursekey", CourseArrayData.getInstance().getCourses().get(position).getKey());
        intent.putExtra("position",position);
        intent.putExtra("Role", role);
        startActivity(intent);
        finish();
    }

    public void DeleteCourseB(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete course")
                .setMessage("Are you sure you want to delete course?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference newD = root.child("Tables");

                        Map<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/Courses/" + key, null);

                        newD.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.delete_failed_message), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.delete_course_message), Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getApplicationContext(), CourseRegistrationActivity.class);
                                    intent.putExtra("Role", role);
                                    startActivity(intent);
                                    finish();
                                }
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("Role", role);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

