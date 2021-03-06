package com.goodthinking.youngloduser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodthinking.youngloduser.user.R;
import com.goodthinking.youngloduser.model.Event;
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

import java.util.HashMap;
import java.util.Map;

public class EventInformationActivity_Firebase extends AppCompatActivity {
    private String IMAGES_BUCKET;
    private TextView ViewEventHeadline, ViewEventdate, ViewEventtime,
            ViewEventSynopsys, ViewEventInfo, ViewEventParticipatorsno, ViewEventHostName, RegistrationIsClosed;
    private DatabaseReference Eventdatabase, MyEventdatabase;
    private int position;
    Button Register;
    String key;
    ImageView imageView;
    private DatabaseReference root;
    private FirebaseAuth mAuth;
    private String keyUserId;
    private Boolean EventIsClosed;
    private FirebaseStorage storage;
    String role;
    String tableName;
    Button users, edit, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information_activity__firebase);
        IMAGES_BUCKET = getString(R.string.images_bucket);

        root = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        Register = (Button) findViewById(R.id.fbviewregtoeventtbtn);

        role = getIntent().getExtras().getString("Role");
        if (role == null) role = "user";
        tableName = getIntent().getExtras().getString("TableName");
        users = (Button) findViewById(R.id.fbviewlistofusersbtn);
        delete = (Button) findViewById(R.id.fbDeleteEventbtn);
        edit = (Button) findViewById(R.id.fbviewEditEventbtn);
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            key = intent.getStringExtra("Eventkey");
            position = intent.getIntExtra("position", 0);

            System.out.println(" " + position + "   " + key);

            if (role == null || !role.equals("manager")) {
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                users.setVisibility(View.INVISIBLE);
            } else {
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                users.setVisibility(View.VISIBLE);
            }
            System.out.println(role);

            ViewEventHeadline = (TextView) findViewById(R.id.fbviewEventHeadlineview);
            ViewEventdate = (TextView) findViewById(R.id.fbviewEventdateview);
            ViewEventtime = (TextView) findViewById(R.id.fbviewEventtimeview);
            ViewEventSynopsys = (TextView) findViewById(R.id.fbviewEventSynopsysview);
            ViewEventInfo = (TextView) findViewById(R.id.fbviewEventInfoview);
            ViewEventParticipatorsno = (TextView) findViewById(R.id.fbviewEventParticipatorsno);
            ViewEventHostName = (TextView) findViewById(R.id.fbviewEventhostname);
            RegistrationIsClosed = (TextView) findViewById(R.id.fbRegistrationIsClosed);
            imageView = (ImageView) findViewById(R.id.imageView2);

            Event event = EventArraydata.getInstance().getEvents().get(position);

            ViewEventHeadline.setText(EventArraydata.getInstance().getEvents().get(position).getEventName());
            ViewEventdate.setText(EventArraydata.getInstance().getEvents().get(position).getEventDate());
            ViewEventtime.setText(EventArraydata.getInstance().getEvents().get(position).getEventTime());
            ViewEventSynopsys.setText(EventArraydata.getInstance().getEvents().get(position).getEventSynopsys());
            ViewEventInfo.setText(EventArraydata.getInstance().getEvents().get(position).getEventInformation());
            ViewEventParticipatorsno.setText(String.valueOf(EventArraydata.getInstance().getEvents().get(position).getEventParticipatorsno()));
            ViewEventHostName.setText(EventArraydata.getInstance().getEvents().get(position).getEventHost());
            EventIsClosed = EventArraydata.getInstance().getEvents().get(position).getEventIsClosed();


            StorageReference storageRef = storage.getReferenceFromUrl(IMAGES_BUCKET);


            if (event.getImage() != null && event.getImage().length() > 0) {

                String[] parts = event.getImage().split("/");
                storageRef.child("/images/" + parts[parts.length - 1]).getBytes(1024 * 1024 * 5).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bits) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bits, 0, bits.length);
                        imageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }


            if (EventIsClosed == true) {
                Register.setEnabled(false);
                RegistrationIsClosed.setVisibility(View.VISIBLE);
            }
        }

        checkRegistration();
    }

    private void checkRegistration() {
        if (mAuth.getCurrentUser() != null && !mAuth.getCurrentUser().isAnonymous()) {
            keyUserId = mAuth.getCurrentUser().getUid();
            System.out.println("keyUserId   " + keyUserId + " key=" + key);
            root.child("Tables").child(tableName).child(key).child("Applicants").child(keyUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot == null || snapshot.getValue() == null) {
                        Register.setText(R.string.register_event_btn);
                        //  Toast.makeText(EventInformationActivity_Firebase.this, "No record found",
                        //          Toast.LENGTH_SHORT).show();
                    } else {
                        Register.setText(R.string.update_register_event_btn);
                        // Toast.makeText(EventInformationActivity_Firebase.this, snapshot.getValue().toString(),
                        //         Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Download failed" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Register.setText(R.string.register_event_btn);
        }
    }

    public void EditEventbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), EventAddNew_Firebase.class);
        intent.putExtra("Eventkey", key);
        intent.putExtra("Role", role);
        intent.putExtra("TableName", tableName);
        startActivity(intent);
        finish();
    }

    public void CancelAddEventbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Role", role);
        intent.putExtra("TableName", tableName);
        startActivity(intent);
        finish();
    }

    public void RegisterEventbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), EventRegisterationActivity.class);
        intent.putExtra("Role", role);
        intent.putExtra("TableName", tableName);
        intent.putExtra("Eventkey", key);
        intent.putExtra("position", position);

        startActivity(intent);
        finish();
    }

    public void Listofuserstbtn(View view) {
        Intent intent = new Intent(getApplicationContext(), Applicant_list.class);
        intent.putExtra("Role", role);
        intent.putExtra("TableName", tableName);
        startActivity(intent);
        finish();
    }


    public void DeleteEventB(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Activity")
                .setMessage("Are you sure you want to delete Activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference newD = root.child("Tables");

                        Map<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/" + tableName + "/" + key, null);

                        newD.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.delete_failed_message), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.delete_event_message), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), EventRegisterationActivity.class);
                                    intent.putExtra("Role", role);
                                    intent.putExtra("TableName", tableName);
                                    intent.putExtra("Eventkey", key);
                                    intent.putExtra("position", position);
                                    startActivity(intent);
                                    finish();
                                }
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("Role", role);
                                intent.putExtra("TableName", tableName);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}