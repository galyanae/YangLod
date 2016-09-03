package com.goodthinking.younglod.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.newsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TreeMap;

public class NewsActivity extends AppCompatActivity {

    FloatingActionButton fab;
    AlertDialog show;
    private DatabaseReference root;
    DatabaseReference newsRef;
    private TreeMap<String, newsItem> newsArray = new TreeMap();
    protected RecyclerView NewsRecyclerView;
    protected NewsRecyclerAdapter newsRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    String role = "user";
    boolean isManager = false;

    ImageView ivCancel;
    ImageView ivSave;
    ImageView ivPicture;
    static Button bDate;
    static Button bTime;
    EditText etTitle;
    EditText etInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        try {
            role = getIntent().getExtras().getString("Role");
        } catch (Exception e) {
            role = "user";
        }
        if (role.equals("manager")) {
            isManager = true;

        } else {
            fab.setVisibility(View.GONE);
        }
        System.out.println("Am I a manager? " + isManager);
        NewsRecyclerView = (RecyclerView) findViewById(R.id.rvYedias);
        linearLayoutManager = new LinearLayoutManager(this);

        root = FirebaseDatabase.getInstance().getReference();
        newsRef = root.child("Tables").child("news");
        // Read ONCE all news
        //
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(getClass().getName(), "newsCount=" + dataSnapshot.getChildrenCount());
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String key = snap.getKey();
                    newsItem newsItem = new newsItem();
                    newsItem = snap.getValue(newsItem.class); // load the news details
                    newsItem.setKey(snap.getKey());
                    System.out.println("inserting newsArray=" + newsItem.toString());


                    if (newsItem.getImage() != null && newsItem.getImage().length() > 0) {
                        byte[] decodedString = Base64.decode(newsItem.getImage(), Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        newsItem.setImg(bmp);
                    }
                    // the key construct from date+time+count, ensures that news will be sorted according to their date
                    // and time
                    newsArray.put(newsItem.getDate() + newsItem.getTime() + newsItem.getKey(), newsItem);
                    System.out.println(newsArray.size());
                }
                // time to refresh event details
                System.out.println("2nd" + linearLayoutManager.toString());
                // next two lines tells the recycler to list the news from bottom to top means last news will be displayed 1st
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                NewsRecyclerView.setLayoutManager(linearLayoutManager);
                newsRecyclerAdapter = new NewsRecyclerAdapter(newsArray, this);
                NewsRecyclerView.setAdapter(newsRecyclerAdapter);
                newsRecyclerAdapter.notifyDataSetChanged();
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_off) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(NewsActivity.this, LoginActivity_Firebase.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newstime(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        String str = bTime.getText().toString();
        int hour = Integer.parseInt(str.substring(0, 2));
        int minute = Integer.parseInt(str.substring(3, 5));
        System.out.println("hour=" + hour + " minute=" + minute);
        Bundle args = new Bundle();
        args.putInt("hour", hour);
        args.putInt("minute", minute);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void newsdate(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        String str = bDate.getText().toString();
        int day = Integer.parseInt(str.substring(0, 2));
        int month = Integer.parseInt(str.substring(3, 5));
        int year = Integer.parseInt(str.substring(6));
        System.out.println("year=" + year + " month=" + month + " day=" + day);
        month--;
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        newFragment.setArguments(args);

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void addNews(View v) {
        Toast.makeText(NewsActivity.this, "fab clicked", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.new_news, null, false);

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etInfo = (EditText) view.findViewById(R.id.etInfo);
        ivCancel = (ImageView) view.findViewById(R.id.ivCancel);
        ivSave = (ImageView) view.findViewById(R.id.ivSave);
        ivPicture = (ImageView) view.findViewById(R.id.ivPicture);
        bDate = (Button) view.findViewById(R.id.bDate);
        bTime = (Button) view.findViewById(R.id.bTime);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        bDate.setText(String.format("%02d/%02d/%04d", day, month, year));

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        bTime.setText(String.format("%02d:%02d", hour, minute));

        builder.setView(view);
        show = builder.show();
        show.setView(v);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle args = getArguments();
            int hour = args.getInt("hour", 0);
            int minute = args.getInt("minute", 0);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            bTime.setText(String.format("%02d:%02d", hourOfDay, minute));

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle args = getArguments();
            int year = args.getInt("year", 0);
            int month = args.getInt("month", 0);
            int day = args.getInt("day", 0);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            month++;
            System.out.println("year=" + year + " month=" + month + " day=" + day);
            String startDate = String.format("%04d-%02d-%02d", year, month, day);
            bDate.setText(String.format("%02d/%02d/%04d", day, month, year));
            String YEAR = startDate.substring(0, 4);
            String MONTH = startDate.substring(5, 7);
            String DAY = startDate.substring(8);
            System.out.println("Year" + YEAR + " month" + MONTH + " day" + DAY);
        }
    }
}
