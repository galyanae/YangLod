package com.goodthinking.younglod.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goodthinking.younglod.user.model.Event;
import com.goodthinking.younglod.user.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EventRecyclerview_Firebase extends AppCompatActivity {
    private RecyclerView EventRecyclerView;
    private EventRecyclerAdapter EventRecyclerAdapter;
    private FirebaseAuth auth;
    private DatabaseReference Eventdatabase,  MyEventdatabase ;
    private String flag_to_myEvents;
    FloatingActionButton fab;

    private Query queryRef;

    private DatabaseReference Userdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_recyclerview__firebase);
        EventRecyclerView = (RecyclerView) findViewById(R.id.EventRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        EventRecyclerView.setLayoutManager(linearLayoutManager);
        EventRecyclerAdapter = new EventRecyclerAdapter(getApplicationContext());
        EventRecyclerView.setAdapter(EventRecyclerAdapter);
        EventRecyclerAdapter.notifyDataSetChanged();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshallEvents();

    }

    private void RefreshallEvents() {

        Eventdatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refEvents = Eventdatabase.child("Tables").child("Events");
        queryRef = refEvents.orderByChild("statusIsValidDate").startAt("1");
      //  Eventdatabase.child("Amuta").child("Events").addListenerForSingleValueEvent(new ValueEventListener() {
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //EventToArrayData(dataSnapshot);
                EventArraydata.getInstance().getEvents().clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Event event = data.getValue(Event.class);
                    event.setKey(data.getKey());
                    EventArraydata.getInstance().getEvents().add(event);
                }
                EventRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadUser(String userID) {
        Userdatabase.child("users").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User newUser = dataSnapshot.getValue(User.class);

                        String name = newUser.getUserName();
                        String role = newUser.getRole();
                        if (role.equals("manager")){
                            fab.setVisibility(View.VISIBLE) ;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    public void addNewEvent(View view) {
        Intent intent=new Intent(EventRecyclerview_Firebase.this,EventAddNew_Firebase.class);
        startActivity(intent);

    }}