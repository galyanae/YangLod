package com.goodthinking.younglod.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.goodthinking.younglod.user.model.Yedia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeMap;

public class NewsActivity extends AppCompatActivity {

    FloatingActionButton fab;
    AlertDialog show;
    private DatabaseReference root;
    DatabaseReference newsRef;
    private TreeMap<String, Yedia> newsArray = new TreeMap();
    protected RecyclerView NewsRecyclerView;
    protected NewsRecyclerAdapter newsRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    boolean isManager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
/*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // Activate Login
            startActivity(new Intent(NewsActivity.this, LoginActivity.class));
        } else {
            NewsRecyclerView = (RecyclerView) findViewById(R.id.rvYedias);
            linearLayoutManager = new LinearLayoutManager(this);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            root = FirebaseDatabase.getInstance().getReference();
            final String userid = user.getUid();
            System.out.println("userid=" + userid);
/*            root.child("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(getClass().getName(), "userCount=" + dataSnapshot.getChildrenCount());
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String key = snap.getKey();
                        String value = (String) snap.getValue();
                        System.out.println("key=" + key + " " + value);
                        if (key.equals("Role") && value.equals("Manager")) {
                            System.out.println("userid=" + userid + " is Manager");
                            fab.setVisibility(View.VISIBLE);
                            isManager = true;
                            break;
                        }


                    }
                    if (!isManager) fab.setVisibility(View.GONE);
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            });
*/
        NewsRecyclerView = (RecyclerView) findViewById(R.id.rvYedias);
        linearLayoutManager = new LinearLayoutManager(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);  // remove this line when activating manager

        root = FirebaseDatabase.getInstance().getReference();
        newsRef = root.child("Tables").child("news");
        // Read ONCE all news
        //
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(getClass().getName(), "newsCount=" + dataSnapshot.getChildrenCount());
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String key = snap.getKey();
                    Yedia yedia = new Yedia();
                    yedia = snap.getValue(Yedia.class); // load the news details
                    yedia.setKey(snap.getKey());
                    System.out.println("inserting newsArray=" + yedia.toString());
                    if (yedia.getImage() != null && yedia.getImage().length() > 0) {
                        byte[] decodedString = Base64.decode(yedia.getImage(), Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        yedia.setImg(bmp);
                    }
                    // the key construct from date+time+count, ensures that news will be sorted according to their date
                    // and time
                    newsArray.put(yedia.getDate() + yedia.getTime() + yedia.getKey(), yedia);
                    System.out.println(newsArray.size());
                }
                // time to refresh event details
                System.out.println("2nd" + linearLayoutManager.toString());
                // next two lines tells the recycler to list the news from bottom to top means last news will be displayed 1st
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                NewsRecyclerView.setLayoutManager(linearLayoutManager);
                newsRecyclerAdapter = new NewsRecyclerAdapter(newsArray);
                NewsRecyclerView.setAdapter(newsRecyclerAdapter);
                newsRecyclerAdapter.notifyDataSetChanged();
                updateNews();
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // }
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


    public void updateNews() {
        for (Object key : newsArray.keySet()) {
            System.out.println("key=" + (String) key + "" + newsArray.get(key).toString());
        }
        // now read the events data and update newsArray accordingly
        /*if (newsArray.size() > 0) {
            Query queryRef = newsRef.startAt(newsArray.firstKey()).endAt(newsArray.lastKey());
        }*/


    }


}
