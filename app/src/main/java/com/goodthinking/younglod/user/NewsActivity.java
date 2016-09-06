package com.goodthinking.younglod.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
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

import com.goodthinking.younglod.user.model.newsItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NewsActivity extends AppCompatActivity implements NewsRecyclerAdapter.RefreshMeCallback {
    private boolean imagePresent = false;
    private String imageName;
    private Bitmap bitmap;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String IMAGES_BUCKET = "gs://hadashot-9bbf1.appspot.com";
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
    String key;
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
            fab.setVisibility(View.VISIBLE);
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
                newsRecyclerAdapter = new NewsRecyclerAdapter(newsArray, NewsActivity.this, isManager);
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
        ivCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cancel(arg0);
            }

        });
        ivSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                saveNews(arg0);
            }

        });
        ivPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                addNewImage(arg0);
            }
        });

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

    public void saveNews(View v) {
        System.out.println("here i am saving data");
        //DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference newsRef = root.child("Tables").child("news");
        String sdate = bDate.getText().toString();
        sdate = sdate.substring(6) + sdate.substring(3, 5) + sdate.substring(0, 2);
        String stime = bTime.getText().toString();
        stime = stime.substring(0, 2) + stime.substring(3);
        newsItem nItem = new newsItem(etInfo.getText().toString(),
                etTitle.getText().toString(),
                sdate,
                stime,
                "nada", "n");

        String str = newsRef.push().getKey();
        System.out.println("saving news to database key=" + str);
        nItem.setKey(str);
        System.out.println(nItem.toString());
        newsArray.put(nItem.getDate() + nItem.getTime() + nItem.getKey(), nItem);

        newsRef.child(str).setValue(nItem.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully." + databaseReference.getKey());
                    key = databaseReference.getKey();
                    if (imagePresent) saveImage(key);
                    newsRecyclerAdapter.notifyDataSetChanged();
                    show.dismiss();
                }
            }
        });
    }

    public void cancel(View v) {
        show.dismiss();
    }

    public void refreshMe() {
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    private void saveImage(String DBid) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // https://firebase.google.com/docs/storage/android/create-reference
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(IMAGES_BUCKET);

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child("images/" + imageName);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        final String keyid = new String(DBid);
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
                children.put("iName", downloadUrl.getLastPathSegment());
                //children.put("image", downloadUrl.getPath());
                System.out.println(downloadUrl.getPath());
                System.out.println(downloadUrl.getLastPathSegment());
                newsRef.child(key).updateChildren(children);
                // signal new image ready
                //now look for the right entry in arrayData and update it.
                System.out.println("keyid="+keyid);
                String DBkey="";
                for (TreeMap.Entry<String, newsItem> entry : newsArray.entrySet()) {
                    DBkey = entry.getKey();
                    if (keyid.equals(DBkey.substring(12))) {
                        newsItem newsItem = new newsItem();
                        newsItem = entry.getValue();
                        newsItem.setiName(downloadUrl.getLastPathSegment());
                        newsArray.put(DBkey, newsItem);
                        newsRecyclerAdapter.notifyDataSetChanged(); //refresh all
                        break;
                    }

                }
                System.out.println("DBkey"+DBkey+ "keyid="+keyid);
                //////////
            }
        });
    }

    public void addNewImage(View view) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            imageName = uri.getPathSegments().get(uri.getPathSegments().size() - 1) + ".jpg";
            imagePresent = false;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //Log.d(getClass().getName(), " "+String.valueOf(bitmap));

                ImageView imageView = (ImageView) show.findViewById(R.id.imageViewNewImage);
                imageView.setImageBitmap(bitmap);
                imagePresent = true;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
