package com.goodthinking.younglod.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.goodthinking.younglod.user.model.MenuIcon;
import com.goodthinking.younglod.user.model.newsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private AdapterOne adapterOne;
    Intent intent;
    MenuIcon menuIcon;
    private DatabaseReference root;
    boolean isManager = false;
    private TreeMap<String, newsItem> newsArray = new TreeMap();
    private String role;
    private String tableName;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        role = getIntent().getExtras().getString("Role");
        if (role == null || role.length() == 0) role = "user";
        auth = FirebaseAuth.getInstance();

        if ("manager".equals(role)) {
            isManager = true;
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/manager");
        }

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/all");

        System.out.println("Am I a manager? " + isManager);
        gridView = (GridView) findViewById(R.id.tableLayout);
        adapterOne = new AdapterOne(getApplicationContext());
        gridView.setAdapter(adapterOne);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                if (adapterOne.getItems().get(position).getName().equals("news")) {
                                                    intent = new Intent(MainActivity.this, NewsActivity.class);
                                                } else if (adapterOne.getItems().get(position).getName().equals("events")) {
                                                    tableName = "Events";
                                                    intent = new Intent(MainActivity.this, EventRecyclerview_Firebase.class);

                                                } else if (adapterOne.getItems().get(position).getName().equals("courses")) {
                                                    tableName = "Courses";
                                                    intent = new Intent(MainActivity.this, EventRecyclerview_Firebase.class);

                                                } else if (adapterOne.getItems().get(position).getName().equals("business")) {
                                                    intent = new Intent(MainActivity.this, EventAddNew_Firebase.class);
                                                } else if (adapterOne.getItems().get(position).getName().equals("contacts")) {
                                                    intent = new Intent(MainActivity.this, AboutUsActivity.class);
                                                } else {
                                                    intent = new Intent(MainActivity.this, ComingSoon.class);
                                                }
                                                intent.putExtra("Role", role);
                                                intent.putExtra("TableName", tableName);
                                                startActivity(intent);
                                            }
                                        }

        );
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_option, menu);
        auth = FirebaseAuth.getInstance();

        if (!"manager".equals(role)) {
            menu.getItem(4).setVisible(false);
            menu.getItem(5).setVisible(false);
            menu.getItem(6).setVisible(false);
        } else {
            menu.getItem(4).setVisible(true);
            menu.getItem(5).setVisible(true);
            menu.getItem(6).setVisible(true);

        }
        if ((auth == null || auth.getCurrentUser() == null || auth.getCurrentUser().isAnonymous())) {
            menu.getItem(0).setVisible(false);  // no my events
            menu.getItem(1).setVisible(false); // no my courses

        } else {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_user_list) {
            //goToMyEventPage();
            Intent intent = new Intent(getApplicationContext(), MyEvent_Activity.class);
            intent.putExtra("Role", role);
            intent.putExtra("TableName", "Events");
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_profile) {
            //goToMyEventPage();
            Intent intent = new Intent(getApplicationContext(), MyEvent_Activity.class);
            intent.putExtra("Role", role);
            intent.putExtra("TableName", "Courses");
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_logout) {
            logout();
            return true;
        } else if (id == R.id.send_email_to_manager) {
            sendEmailToManager();
            return true;
        } else if (id == R.id.send_message_to_users) {
            intent = new Intent(MainActivity.this, NewMessageToUsers.class);
            intent.putExtra("Role", role);
            intent.putExtra("TableName", tableName);
            startActivity(intent);
            return true;
        } else if (id == R.id.eventsValid) {

            intent = new Intent(MainActivity.this, EventRecyclerview_Firebase.class);
            intent.putExtra("typeEvents", "noValid");
            intent.putExtra("Role", role);
            intent.putExtra("TableName", "Events");
            startActivity(intent);
            return true;
        } else if (id == R.id.coursesValid) {

            intent = new Intent(MainActivity.this, EventRecyclerview_Firebase.class);
            intent.putExtra("typeEvents", "noValid");
            intent.putExtra("Role", role);
            intent.putExtra("TableName", "Courses");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity_Firebase.class));

        Toast.makeText(getApplicationContext(), "You Logout successfully", Toast.LENGTH_LONG).show();
    }

    public void sendEmailToManager() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lodyoung1@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}