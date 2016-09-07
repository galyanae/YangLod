package com.goodthinking.younglod.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyEvent_Activity extends AppCompatActivity {
    private RecyclerView EventRecyclerView;
    private EventRecyclerAdapter EventRecyclerAdapter;
    private FirebaseAuth auth;
    private DatabaseReference Eventdatabase, MyEventdatabase;
    ArrayList<String> filter = new ArrayList<>();
    private Boolean EventIsNotValid;
    private FloatingActionButton fab;

    String role;
    String tableName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_recyclerview_firebase);

        role = getIntent().getExtras().getString("Role");
        if (role == null) role = "user";
        tableName = getIntent().getExtras().getString("TableName");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        EventRecyclerView = (RecyclerView) findViewById(R.id.EventRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        EventRecyclerView.setLayoutManager(linearLayoutManager);
        EventRecyclerAdapter = new EventRecyclerAdapter(getApplicationContext(), role, tableName);
        EventRecyclerView.setAdapter(EventRecyclerAdapter);
        EventRecyclerAdapter.notifyDataSetChanged();

        EventRecyclerAdapter.notifyDataSetChanged();
        EventArraydata.getInstance().getEvents().clear();
        RefreshOnlyMyEvents();
    }


    private void RefreshOnlyMyEvents() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UserID = user.getUid();
        //System.out.println(UserID);

        Eventdatabase = FirebaseDatabase.getInstance().getReference();
        MyEventdatabase = Eventdatabase.child("users").child(UserID).child("My" + tableName);
        MyEventdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filter.clear();
                if(dataSnapshot.getChildrenCount()>0) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String key = data.getKey();
                        filter.add(key);
                    }
                    //EventArraydata.getInstance().getEvents().clear();
                    for (int i = 0; i < filter.size(); i++) {
                        final String Eventkey = filter.get(i);
                        System.out.println("key is" + Eventkey);
                        Eventdatabase = FirebaseDatabase.getInstance().getReference();
                        System.out.println(getClass().getName() + " tableName=" + tableName);
                        Eventdatabase.child("Tables").child(tableName).child(Eventkey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Event event = dataSnapshot.getValue(Event.class);
                                event.setKey(dataSnapshot.getKey());
                                EventIsNotValid = event.getEventIsNotValid();
                                if (EventIsNotValid == false) {
                                    EventArraydata.getInstance().getEvents().add(event);
                                }
                                EventRecyclerAdapter.notifyDataSetChanged();
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

//
                    }
                }
                else EventRecyclerAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
