package com.goodthinking.younglod.user;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.goodthinking.younglod.user.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private AdapterOne adapterOne;
    Intent intent;
    Icon icon;
    private DatabaseReference root;
    boolean isManager = false;
    private TreeMap<String, Yedia> newsArray = new TreeMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.tableLayout);
        adapterOne = new AdapterOne(getApplicationContext());
        gridView.setAdapter(adapterOne);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapterOne.getItems().get(position).getName().equals("news")){
                    intent=new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                }
                else if (adapterOne.getItems().get(position).getName().equals("events")){

                    Intent intent=new Intent(MainActivity.this,EventRecyclerview_Firebase.class);
                    startActivity(intent);
                }
                else if (adapterOne.getItems().get(position).getName().equals("courses")){

                    Intent intent=new Intent(MainActivity.this,UserMainActivity.class);
                    startActivity(intent);

                }
                else if (adapterOne.getItems().get(position).getName().equals("business")){
                    Intent intent=new Intent(MainActivity.this,EventAddNew_Firebase.class);
                    startActivity(intent);
                }

                else if (adapterOne.getItems().get(position).getName().equals("hayalim")){
                    Intent intent=new Intent(MainActivity.this,EditActivity.class);
                    startActivity(intent);
                }
            else {
                    Intent intent=new Intent(MainActivity.this,ComingSoon.class);
                    startActivity(intent);
                }}


        });
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String userid;
        if (user == null) {
            userid = "Anonimus";
            System.out.println("User is Anonimus");
        } else {

            userid = user.getUid();
            System.out.println("userid=" + userid);}

            }

        }