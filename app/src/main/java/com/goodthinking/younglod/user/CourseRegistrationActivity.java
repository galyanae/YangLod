package com.goodthinking.younglod.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 03/09/2016.
 */
public class CourseRegistrationActivity extends AppCompatActivity {
    private TextView CourseHeadline, Coursedate, Coursetime,
            CourseSynopsys, UserName, Userphone, Usermail, Rgviewmessage;
    private EditText NoOfParticipatorsField;
    private Button loginbtn, signupbtn, registerbtn, cancelRegisterbtn;
    private String userID;

    private String Usernamestr = "anonymous";
    private String UserPhonestr = "";
    private String UserEmailstr = "";
    private FirebaseAuth auth;
    private DatabaseReference Userdatabase;
    private ProgressDialog progressDialog;
    private int position, UserNoOfParticipators;
    private String key;

    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);

        auth = FirebaseAuth.getInstance();
        Userdatabase = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        NoOfParticipatorsField = (EditText) findViewById(R.id.rgparticipators);
        CourseHeadline = (TextView) findViewById(R.id.rgviewCourseHeadline);
        Coursedate = (TextView) findViewById(R.id.rgviewCoursedate);
        Coursetime = (TextView) findViewById(R.id.rgviewCoursetime);
        CourseSynopsys = (TextView) findViewById(R.id.rgviewCourseSynopsys);
        UserName = (TextView) findViewById(R.id.rgviewUsername);
        Userphone = (TextView) findViewById(R.id.rgviewUserphone);
        Usermail = (TextView) findViewById(R.id.rgviewUseremail);
        loginbtn = (Button) findViewById(R.id.rggotologintbtn);
        signupbtn = (Button) findViewById(R.id.rggotosignuptbtn);
        registerbtn = (Button) findViewById(R.id.rgviewregtocoursetbtn);
        cancelRegisterbtn = (Button) findViewById(R.id.rgCancelRegisterbtn);
        Rgviewmessage = (TextView) findViewById(R.id.rgviewmessage);

        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            key = intent.getStringExtra("Coursekey");
            position = intent.getIntExtra("position", 0);

            if (!key.equals("")) {
                CourseHeadline.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseName());
                Coursedate.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseDate());
                Coursetime.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseTime());
                CourseSynopsys.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseSynopsys());
            }
            if (auth.getCurrentUser() != null) {
                loginbtn.setEnabled(false);
                signupbtn.setEnabled(false);
                //get user from firebase
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserID = user.getUid();
                checkRegistration(UserID);
            } else {
                Toast.makeText(getApplicationContext(), R.string.Registration_toCourse_message, Toast.LENGTH_LONG).show();
                registerbtn.setEnabled(false);
                cancelRegisterbtn.setEnabled(false);
            }
        }

        else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            return;
        }

    }


    private void loadUser(String userID) {
        Userdatabase.child("users").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User newUser = dataSnapshot.getValue(User.class);
                        if (newUser != null) {
                            Usernamestr = newUser.getUserName();
                            UserPhonestr = newUser.getUserPhone();
                            UserEmailstr = newUser.getUserEmail();
                        }

                        UserName.setText(Usernamestr);
                        Userphone.setText(UserPhonestr);
                        Usermail.setText(UserEmailstr);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Download failed"+databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void checkRegistration(String userID) {
        Query qrefUserRegister = Userdatabase.child("Tables").child("Courses").child(key).child("Applicants").child(userID);
        qrefUserRegister.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null) {
                    Rgviewmessage.setText(R.string.Registration_headline);
                    registerbtn.setText(R.string.confirm);
                    cancelRegisterbtn.setEnabled(false);
                    loadUser(UserID);
                    //  Toast.makeText(CourseInformationActivity_Firebase.this, "No record found",
                    //          Toast.LENGTH_SHORT).show();
                } else {
                    Rgviewmessage.setText(R.string.Registration_update_headline);
                    registerbtn.setText(R.string.update);
                    // Toast.makeText(CourseInformationActivity_Firebase.this, snapshot.getValue().toString(),
                    //         Toast.LENGTH_SHORT).show();
                    User newUser = snapshot.getValue(User.class);
                    Usernamestr = newUser.getUserName();
                    UserPhonestr = newUser.getUserPhone();
                    UserEmailstr = newUser.getUserEmail();
                    UserNoOfParticipators = newUser.getUserNoOfParticipators();

                    UserName.setText(Usernamestr);
                    Userphone.setText(UserPhonestr);
                    Usermail.setText(UserEmailstr);
                    NoOfParticipatorsField.setText("" + UserNoOfParticipators);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void RegisterCoursebtn(View view) {

//        UserNoOfParticipators = NoOfParticipatorsField.getText().toString().equals("") ? 0 :
//                Integer.parseInt(NoOfParticipatorsField.getText().toString());

        if (NoOfParticipatorsField.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.Registration_Userparticipators_no, Toast.LENGTH_LONG).show();
            return;
        } else {
            UserNoOfParticipators = Integer.parseInt(NoOfParticipatorsField.getText().toString());
            // DatabaseReference newD = Userdatabase.child("users");
            DatabaseReference newD = Userdatabase;

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userID = user.getUid();
            User Newuser = new User(Usernamestr, UserPhonestr, UserEmailstr, UserNoOfParticipators);
            Newuser.setUserID(userID);
            // Map<String, Object> applicant = new HashMap<>();
            //  applicant.put(userID, Newuser.ApplicanttoMap());


            progressDialog.setMessage(getString(R.string.Progress_Dialog_Register));
            progressDialog.show();
            Map<String, Object> childUpdates = new HashMap<>();

            childUpdates.put("/Tables/Courses/" + key + "/Applicants/" + userID, Newuser.ApplicanttoMap());
            childUpdates.put("/users/"+ userID + "/MyCourses/" + key, "true");
            //newD.updateChildren(childUpdates);
            //progressDialog.dismiss();
            newD.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(getApplicationContext(), getString(R.string.register_faild_message), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.Registration_successful), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), CourseThanksActivity.class);
                        intent.putExtra("courseID", key);
                        startActivity(intent);
                    }
                    FirebaseMessaging.getInstance().subscribeToTopic(key);
                    progressDialog.dismiss();

                    // startActivity(new Intent(getApplicationContext(), EventThanksActivity.class));
                    finish();

                }
            });
        }
    }

    public void loginCoursebtn(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity_Firebase.class));
        finish();
    }

    public void Cancelbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("UserName", Usernamestr);
        startActivity(intent);
        finish();
    }

    public void signupCoursebtn(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity_Firebase.class));
        finish();
    }

    public void CancelRegisterbtn(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cancel Confirmation")
                .setMessage("Are you sure you want to cancel registration?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference newD = Userdatabase.child("users");

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        userID = user.getUid();
                        Map<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/Tables/Courses/" + key + "/Applicants/" + userID, null);
                        childUpdates.put("/users/" + userID + "/MyCourses/" + key, null);

                        newD.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.cancel_faild_message), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.Registration_cancel_successful), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), CourseRegistrationActivity.class));
                                    finish();
                                }
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(key);
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), CourseThanksActivity.class);
                                intent.putExtra("courseID", key);
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

