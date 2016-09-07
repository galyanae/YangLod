package com.goodthinking.younglod.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.goodthinking.younglod.user.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Applicant_list extends AppCompatActivity {
    private TextView EventHeadLine, TotalOfApplicants;
    private RecyclerView Applicantlist;
    private Applicant_List_Adapter applicant_list_adapter;
    private DatabaseReference applicantsdatabase;
    private int position;
    private String key;
    private String Usernamestr, UserPhonestr, UserEmailstr,NoOfParticipators;
    private int amountOfApplicants;
String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_list);

        EventHeadLine= (TextView) findViewById(R.id.applicantlistheadline);
        TotalOfApplicants = (TextView) findViewById(R.id.totalApplicantsNumber);

        Applicantlist= (RecyclerView) findViewById(R.id.applicantRecyclerview);
        applicant_list_adapter=new Applicant_List_Adapter(getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        Applicantlist.setLayoutManager(linearLayoutManager);
        Applicantlist.setAdapter(applicant_list_adapter);
        applicant_list_adapter.notifyDataSetChanged();

        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            tableName=intent.getStringExtra("TableName");
            key = intent.getStringExtra("Eventkey");
            position = intent.getIntExtra("position", 0);
            if (!key.equals("")) {
                EventHeadLine.setText(EventArraydata.getInstance().getEvents().get(position).getEventName());
            }
        }
    }
    @Override
    protected void onResume() {
        amountOfApplicants = 0;
        super.onResume();
        applicantsdatabase= FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference=applicantsdatabase.child("Tables").child(tableName).child(key).child("Applicants");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                applicant_list_adapter.getApplicantList().clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //User user=new User(Usernamestr, UserPhonestr, UserEmailstr,NoOfParticipators);
                    User user = data.getValue(User.class);
                    //System.out.println("participants"+user.getUserNoOfParticipators());
                    //System.out.println("password" + user.getUserPassword());
                    amountOfApplicants +=  user.getUserNoOfParticipators();
                    applicant_list_adapter.getApplicantList().add(user);

                  //  System.out.println("amount" + user.getUserNoOfParticipators());
                  //  System.out.println("total" + amountOfApplicants);
                }
                applicant_list_adapter.notifyDataSetChanged();
                TotalOfApplicants.setText("" + amountOfApplicants);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
