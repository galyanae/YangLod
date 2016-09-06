package com.goodthinking.younglod.user;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewMessageToUsers extends AppCompatActivity {


    //TODO: This API key is wrong, need to get another from 'Manage -> Cloud Messaging'
    private static final String API_KEY = "AIzaSyApaL01LVo4ozBZgIvuAKpWMu3e-qSYa88";
    private Map<String, String> events = null;
    private Context ctx;

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

        ctx = this;

        events = new HashMap<>();
        events.put("all", "All");
        db.child("Tables").child("Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    if (child != null && child.child("EventName") != null) {
                        String eventName = (String) child.child("EventName").getValue();
                        String eventId =  child.getKey();
                        if (eventName != null) {
                            events.put(eventName, eventId);
                        }
                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ctx,
                        android.R.layout.simple_spinner_dropdown_item,
                        new ArrayList<>(events.keySet()));

                Spinner spinner = (Spinner) findViewById(R.id.eventsSpinner);
                spinner.setAdapter(adapter);

                setOnItemSelected(spinner);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }


        });
    }

    private void setOnItemSelected(Spinner spinner) {
        spinner.setOnItemSelectedListener(new EventsSpinnerOnItemSelectedListener());
    }

    private void success() {
        Toast.makeText(ctx, "Message was sended sucsessfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private class SendMessageToUsersClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView sendTextView = (TextView) findViewById(R.id.sendText);

            final String textToSend = sendTextView.getText().toString().trim();

            if (textToSend.length() > 0) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        sendToTopic(textToSend);
                    }
                });
            }
            success();
        }

        private void sendToTopic(String textToSend) {
            try {
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection)  url.openConnection();
                    send(textToSend, conn);

                    int responseCode = conn.getResponseCode();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void send(String textToSend, HttpURLConnection conn) throws IOException, JSONException {
            conn.setRequestProperty("Content-Type",
                    "application/json");
            conn.setRequestProperty("Authorization", "key="+API_KEY);

            conn.setRequestMethod("POST");

            DataOutputStream wr = new DataOutputStream (
                    conn.getOutputStream ());
            wr.writeBytes (getPayload(selectedItemId, textToSend));
            wr.flush ();
            wr.close ();
        }

        private String getPayload(String selectedItemId, String textToSend) throws JSONException {

            JSONObject data = new JSONObject();

            data.put("message", textToSend);

            JSONObject payload = new JSONObject();

            payload.put("to", "/topics/"+selectedItemId);
            payload.put("data", data);

            return payload.toString();
        }
    }

    private String selectedItemId;

    private class EventsSpinnerOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = parent.getItemAtPosition(position).toString();

            selectedItemId = events.get(selectedItem);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
