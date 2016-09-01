package com.goodthinking.younglod.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.Course;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;


public class EditActivity extends AppCompatActivity {

                private DatabaseReference root;//point on a specific place in the db
    private String CourseHeadLine;
    private String CourseStartdate;
    private String Coursetime;
    private String CourseGide;
    private String CourseSynopsys;
    private String CourseInfo;
    private String CourseEndDate;
    private String MaxNoOfParticipetors;
    private int numberPrticipate;
    private String CourseCost;
    private String CourseAduience;
    private String CourseLang;
    private String StatusIsValidDate;

                protected static Button bTime;
        protected static Button bStartDate;
        protected static Button bEndDate;
        Spinner audienceSpinner;
        Spinner spinner;
        Query qref;
        ArrayAdapter<CharSequence> audienceAdapter;
        ArrayAdapter<CharSequence> adapter;

               @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_edit);

                        //read from db
                               root = FirebaseDatabase.getInstance().getReference();
                audienceSpinner = (Spinner) findViewById(R.id.editAudience);
                audienceAdapter = ArrayAdapter.createFromResource(this,
                                R.array.poplulations_array, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                        audienceAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        // Apply the adapter to the spinner
                        audienceSpinner.setAdapter(audienceAdapter);
                spinner = (Spinner) findViewById(R.id.editLanguge);
                adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                spinner.setAdapter(adapter);// Apply the adapter to the spinner
            }

                public void showDatePickerDialog(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                bStartDate = (Button) findViewById(R.id.bStartDate);
                bEndDate = (Button) findViewById(R.id.bEndDate);
                Bundle args = new Bundle();
                args.putInt("fieldID", v.getId());        // obtaining the clicked button id and supply it to the fragment
                newFragment.setArguments(args);

                        newFragment.show(getSupportFragmentManager(), "datePicker");
            }
                    public void showTimePickerDialog(View v) {
                bTime = (Button) findViewById(R.id.bTime);
                //bEndTime = (Button) findViewById(R.id.bEndDate);
                        DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }

                public void save(View view) {
                String CourseHeadLine = ((EditText) findViewById(R.id.HogName)).getText().toString();
                String CourseSynopsys = ((EditText) findViewById(R.id.editDetails2)).getText().toString();
                String CourseInfo = ((EditText) findViewById(R.id.editDetails)).getText().toString();
                String CourseGide = ((EditText) findViewById(R.id.editLecturer)).getText().toString();
                String numberPrticipate = ((EditText) findViewById(R.id.editParticipantNumber)).getText().toString();

                        int pos = audienceSpinner.getSelectedItemPosition();
                String CourseAduience = (String) audienceAdapter.getItem(pos);
                pos = spinner.getSelectedItemPosition();
                String lang = (String) adapter.getItem(pos);
                String CourseCost = ((EditText) findViewById(R.id.editCost)).getText().toString();


                               CourseStartdate = (String) bStartDate.getText();
                CourseEndDate = (String) bEndDate.getText();
                Boolean show = true;
                int img = R.drawable.hide;

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Tables");
        /*
 + public Course(String courseAduience, String courseCost, String courseEndDate, String courseGide,
 +                  String courseHeadLine, XXXX String courseInfo, String courseLang, String courseStartdate,
 +                  String courseSynopsys, String coursetime, String maxNoOfParticipetors,
 +                  String numberPrticipate, String statusIsValidDate)
 + */
                        String statusIsValidDate = "1"+CourseStartdate;
                Course course = new Course(statusIsValidDate, CourseAduience,
                         CourseCost,  CourseEndDate,
                        CourseGide, CourseHeadLine,
                        CourseInfo, CourseLang,
                        CourseStartdate, CourseSynopsys,
                        Coursetime,
                        MaxNoOfParticipetors,
                        0,
                        true);
                myRef.child("hogim").push().setValue(course,
                                new DatabaseReference.CompletionListener() {
                                        //on complete-  check if there is a error whene setting values

                                                @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                if (databaseError != null) {
                                                        Toast.makeText(EditActivity.this, "Creation failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(EditActivity.this, "successfully created", Toast.LENGTH_LONG).show();

                                                            }
                                            }
                                    });

                    }

                public void Delete(View view) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Tables").child("Courses");
                myRef.child("Courses").removeValue();

                    }


                public static class TimePickerFragment extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

                        private String Coursetime;

                        @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {

                                final Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int minute = c.get(Calendar.MINUTE);


                                        return new TimePickerDialog(getActivity(), this, hour, minute,
                                        DateFormat.is24HourFormat(getActivity()));
                    }

                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                Coursetime = String.format("%02d:%02d", hourOfDay, minute);
                        bTime.setText(Coursetime);

                            }
            }

                public static class DatePickerFragment extends DialogFragment
                implements DatePickerDialog.OnDateSetListener {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {

                                final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        return new DatePickerDialog(getActivity(), this, year, month, day);
                    }

                        public void onDateSet(DatePicker view, int year, int month, int day) {

                                Bundle args = getArguments();
                        int field = args.getInt("fieldID", 0);
                        month++;
                        System.out.println("year=" + year + " month=" + month + " day=" + day);
                        if (field == R.id.bStartDate) {
                                String startDate = String.format("%04d-%02d-%02d", year, month, day);
                                bStartDate.setText(String.format("%s: %02d/%02d/%04d", getString(R.string.startDateTitle), day, month, year));
                                String YEAR = startDate.substring(0, 4);
                                String MONTH = startDate.substring(5, 7);
                                String DAY = startDate.substring(8);
                                System.out.println("Year" + YEAR + " month" + MONTH + " day" + DAY);
                                bStartDate.setText(startDate);

                                    } else {
                                String endDate = String.format("%04d-%02d-%02d", year, month, day);
                                bEndDate.setText(String.format("%s: %02d/%02d/%04d", getString(R.string.endDateTitle), day, month, year));
                                String YEAR = endDate.substring(0, 4);
                                String MONTH = endDate.substring(5, 7);
                                String DAY = endDate.substring(8);
                                System.out.println("Year" + YEAR + " month" + MONTH + " day" + DAY);
                                bEndDate.setText(endDate);
                            }
                    }
            }
    }