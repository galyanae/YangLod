package com.goodthinking.younglod.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    private DatabaseReference Eventdatabase,  MyEventdatabase ;
    ArrayList<String> filter = new ArrayList<>();

    private Boolean EventIsNotValid;

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

        EventRecyclerAdapter.notifyDataSetChanged();
        EventArraydata.getInstance().getEvents().clear();
        RefreshOnlyMyEvents();
    }


    private void RefreshOnlyMyEvents() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UserID = user.getUid();
        //System.out.println(UserID);

        Eventdatabase = FirebaseDatabase.getInstance().getReference();
        MyEventdatabase = Eventdatabase.child("Amuta").child("users").child(UserID).child("MyEvents");
        MyEventdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filter.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey();
                    //System.out.println("MyEvent" + key);
                    filter.add(key);
                }
                //EventArraydata.getInstance().getEvents().clear();
                for (int i = 0; i < filter.size(); i++) {
                    final String Eventkey = filter.get(i);
                    System.out.println("key is" + Eventkey);
                    Eventdatabase = FirebaseDatabase.getInstance().getReference();
                    Eventdatabase.child("Amuta").child("Events").child(Eventkey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Event event = dataSnapshot.getValue(Event.class);
                            event.setKey(dataSnapshot.getKey());
                            //String CheckEventKey = event.getKey();
                            //System.out.println("CheckEventkey" + CheckEventKey);
                            EventIsNotValid = event.getEventIsNotValid();
                            if (EventIsNotValid == false){
                            EventArraydata.getInstance().getEvents().add(event);}
                            EventRecyclerAdapter.notifyDataSetChanged();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

//
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//
    }

    @Override
    protected void onResume() {
        super.onResume();
        //EventArraydata.getInstance().getEvents().clear();
    }
}
