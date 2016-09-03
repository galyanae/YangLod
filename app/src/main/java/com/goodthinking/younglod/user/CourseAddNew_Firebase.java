package com.goodthinking.younglod.user;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.goodthinking.younglod.user.CourseArraydata;
import com.goodthinking.younglod.user.CourseRecyclerview_Firebase;
import com.goodthinking.younglod.user.R;
import com.goodthinking.younglod.user.model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 03/09/2016.
 */
    public class CourseAddNew_Firebase extends AppCompatActivity {

        private static final int PICK_IMAGE_REQUEST =1 ;
        private static final String IMAGES_BUCKET = "gs://hadashot-9bbf1.appspot.com";
        private EditText AddCourseHeadline, AddCourseSynopsys, AddCourseInfo, AddCourseParticipatorsno, AddCourseHostName;
        private TextView AddCoursedate, AddCoursetime;
        private Button AddCoursebtn,EditCoursebtn;
        private DatabaseReference Coursedatabase;
        private ProgressDialog progressDialog;
        private int position;
        private int mYear, mMonth, mDay, mHour, mMinute;
        private String key;

        private String timeCourse;
        private String dateCourse;
        private CheckBox courseIsNotValid;
        private CheckBox courseIsClosed;
        private String imageName;
        private Bitmap bitmap;
        String role;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_add_new_firebase);

            progressDialog = new ProgressDialog(this);

            AddCourseHeadline = (EditText) findViewById(R.id.fbAddCourseHeadlineview);
            AddCoursedate = (TextView) findViewById(R.id.fbAddCoursedateview);
            AddCoursetime = (TextView) findViewById(R.id.fbAddCoursetimeview);
            AddCourseSynopsys = (EditText) findViewById(R.id.fbAddCourseSynopsysview);
            AddCourseInfo = (EditText) findViewById(R.id.fbAddCourseInfoview);
            AddCourseParticipatorsno = (EditText) findViewById(R.id.fbAddCourseParticipatorsno);
            AddCourseHostName = (EditText) findViewById(R.id.fbCoursehostname);
            AddCoursebtn = (Button) findViewById(R.id.fbSaveNewCoursebtn);
            EditCoursebtn= (Button) findViewById(R.id.fbEditCoursebtn);
            courseIsNotValid = (CheckBox) findViewById(R.id.editCourseIsValidCheckBox);
            courseIsClosed = (CheckBox) findViewById(R.id.editCourseIsClosedCheckBox);


            if (getIntent().getExtras() != null) {
                Intent intent = getIntent();
                key = intent.getStringExtra("Coursekey");
                position = intent.getIntExtra("position", 0);

                // Bundle extras = getIntent().getExtras();
                // Event event=(Event)extras.get(

                // Event event = (Event) intent.getSerializableExtra("Event");
                //   if (key != " ") {
                if (!key.equals("")) {
                    AddCourseHeadline.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseName());
                    AddCoursedate.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseDate());
                    AddCoursetime.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseTime());
                    AddCourseSynopsys.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseSynopsys());
                    AddCourseInfo.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseInformation());
                    AddCourseParticipatorsno.setText(String.valueOf(CourseArraydata.getInstance().getCourses().get(position).getCourseParticipatorsno()));
                    AddCourseHostName.setText(CourseArraydata.getInstance().getCourses().get(position).getCourseHost());

                    courseIsNotValid.setChecked(CourseArraydata.getInstance().getCourses().get(position).getCourseIsNotValid());
                    courseIsClosed.setChecked(CourseArraydata.getInstance().getCourses().get(position).getCourseIsClosed());

                    AddCoursebtn.setEnabled(false);
                    EditCoursebtn.setEnabled(true);
                }
                else{
                    EditCoursebtn.setEnabled(false);
                }

            }

            Coursedatabase = FirebaseDatabase.getInstance().getReference();
            setDateField();
            setTimeField();

        }

        private void setDateField() {
            AddCoursedate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mCurrentdate = Calendar.getInstance();
                    mYear = mCurrentdate.get(Calendar.YEAR);
                    mMonth = mCurrentdate.get(Calendar.MONTH);
                    mDay = mCurrentdate.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(CourseAddNew_Firebase.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    monthOfYear++;
                                    // AddCoursedate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    AddCoursedate.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear, year));
                                    dateCourse = String.format("%04d-%02d-%02d", year, monthOfYear, dayOfMonth);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.show();
                }


            });
        }

        private void setTimeField() {
            AddCoursetime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    mMinute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(CourseAddNew_Firebase.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            //  AddCoursetime.setText(selectedHour + ":" + selectedMinute);
                            timeCourse = String.format("%02d:%02d", selectedHour, selectedMinute);
                            AddCoursetime.setText(timeCourse);
                        }
                    }, mHour, mMinute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

        }


        public void SaveNewCoursebtn(View view) {
            DatabaseReference newCourse = Coursedatabase.child("Tables").child("Courses");
            key = newCourse.push().getKey();
            saveImage();
            String CourseHeadLine = AddCourseHeadline.getText().toString().trim();
            if (CourseHeadLine.equals("")){
                Toast.makeText(getApplicationContext(), R.string.must_headline, Toast.LENGTH_LONG).show();
                return;
            }

            String Coursedate = AddCoursedate.getText().toString().trim();
            String Coursetime = AddCoursetime.getText().toString().trim();
            String CourseSynopsys = AddCourseSynopsys.getText().toString().trim();
            String CourseInfo = AddCourseInfo.getText().toString().trim();
            String CourseHost = AddCourseHostName.getText().toString().trim();
            int MaxNoOfParticipetors = AddCourseParticipatorsno.getText().toString().equals("") ? 0 : Integer.parseInt(AddCourseParticipatorsno.getText().toString());

            Boolean CourseIsNotValid = courseIsNotValid.isChecked();
            Boolean CourseIsClosed = courseIsClosed.isChecked();

            String StatusIsValidDate;

            if (courseIsNotValid.isChecked() == true) {
                StatusIsValidDate = "0-"+dateCourse.trim();

            }
            else {
                StatusIsValidDate = "1-"+dateCourse.trim();
            }

            String image = "";
            Course course = new Course(CourseHeadLine, Coursedate, Coursetime,
                    CourseSynopsys, CourseInfo, CourseHost, CourseIsNotValid, MaxNoOfParticipetors, CourseIsClosed, StatusIsValidDate, image);
            course.setKey(key);
            //Map<String, Object> coursenew = new HashMap<>();
            //coursenew.put(key, course.Objecttofirebase());

            progressDialog.setMessage("Adding new Course...");
            progressDialog.show();

            newCourse.child(key).setValue(course, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(getApplicationContext(), "Creation failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "successfully created", Toast.LENGTH_LONG).show();

                    }
                    progressDialog.dismiss();
                }
            });

        }

        private void saveImage() {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // https://firebase.google.com/docs/storage/android/create-reference
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReferenceFromUrl(IMAGES_BUCKET);

            // Create a reference to "mountains.jpg"
            StorageReference mountainsRef = storageRef.child("images/"+imageName);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Map<String, Object> children = new HashMap<String, Object>();
                    children.put("image", downloadUrl.getPath());
                    Coursedatabase.child("Tables").child("Courses").child(key).updateChildren(children);
                    finish();
                }
            });

        }

        public void CancelAddCoursebtn(View view) {
            Intent intent=new Intent(getApplicationContext(),CourseRecyclerview_Firebase.class);
            intent.putExtra("Role", role);
            startActivity(intent);
            finish();
        }


        public void addNewImage(View view) {
            Intent intent = new Intent();
// Show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("Role", role);
// Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        }

        /**
         * Activity result is image upload
         * @param requestCode
         * @param resultCode
         * @param data
         */
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST &&
                    resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();
                imageName = uri.getPathSegments().get(uri.getPathSegments().size() - 1) + ".jpg";

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));

                    ImageView imageView = (ImageView) findViewById(R.id.imageViewNewImage);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
