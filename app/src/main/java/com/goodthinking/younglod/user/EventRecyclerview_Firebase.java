package com.goodthinking.younglod.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.Event;
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
    private DatabaseReference Eventdatabase, MyEventdatabase;
    private String flag_to_myEvents;
    FloatingActionButton fab;
    boolean isManager = false;
    String role = "user";
    private Query queryRef;
    private String vTypeEv="Valid";
    String tableName = "Events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_recyclerview_firebase);
        Intent intent = getIntent();

        role = intent.getStringExtra("Role");
        if (role==null) role = "user";
        tableName = intent.getStringExtra("tableName");

        vTypeEv = intent.getStringExtra("typeEvents");
        if (vTypeEv == null || vTypeEv.length()==0) vTypeEv="Valid";
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (role.equals("manager")) {
            isManager = true;
            fab.setVisibility(View.VISIBLE);
        }
        else fab.setVisibility(View.GONE);


        System.out.println("Am I a manager? " + isManager);

        EventRecyclerView = (RecyclerView) findViewById(R.id.EventRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        EventRecyclerView.setLayoutManager(linearLayoutManager);
        EventRecyclerAdapter = new EventRecyclerAdapter(getApplicationContext());
        EventRecyclerView.setAdapter(EventRecyclerAdapter);
        EventRecyclerAdapter.notifyDataSetChanged();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), EventAddNew_Firebase.class);
                intent.putExtra("Role", role);
                intent.putExtra("tableName", tableName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshallEvents();
    }

    private void RefreshallEvents() {

        Eventdatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refEvents = Eventdatabase.child("Tables").child(tableName);
        if (vTypeEv.equals("Valid")) {
            queryRef = refEvents.orderByChild("StatusIsValidDate").startAt("1");
        } else {
            queryRef = refEvents.orderByChild("StatusIsValidDate").startAt("0").endAt("1");
        }
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

                if (EventArraydata.getInstance().getEvents().size() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.no_events_to_show, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void addNewEvent(View view) {
        Intent intent = new Intent(EventRecyclerview_Firebase.this, EventAddNew_Firebase.class);
        intent.putExtra("Role", role);
        intent.putExtra("tableName", tableName);
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }
}