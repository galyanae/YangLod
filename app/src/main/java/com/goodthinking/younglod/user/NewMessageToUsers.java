package com.goodthinking.younglod.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewMessageToUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message_to_users);


        Button sendMessageToUsersButton = (Button) findViewById(R.id.sendButton);

        populateSpinner();

        sendMessageToUsersButton.setOnClickListener(new SendMessageToUsersClickListener());
    }

    private void populateSpinner() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        final List<String> events = new ArrayList<>();
        db.child("Tables").child("Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    if (child != null && child.child("eventName") != null) {
                        String eventName = (String) child.child("eventName").getValue();
                        events.add(eventName);
                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_single_choice,
                        events);

                Spinner spinner = (Spinner) findViewById(R.id.eventsSpinner);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }


        });
    }

    private class SendMessageToUsersClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView sendTextView = (TextView) findViewById(R.id.sendText);

            String textToSend = sendTextView.getText().toString().trim();

            if (textToSend.length() > 0) {

            }

        }
    }

}
