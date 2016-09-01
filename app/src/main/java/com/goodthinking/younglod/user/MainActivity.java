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
import com.goodthinking.younglod.user.model.Yedia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private AdapterOne adapterOne;
    Intent intent;
    MenuIcon menuIcon;
    private DatabaseReference root;
    boolean isManager = false;
    private TreeMap<String, Yedia> newsArray = new TreeMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String role = "user";
        try {
                        role = getIntent().getExtras().getString("Role");
                    } catch (Exception e) {
                        role = "user";
                    }
                if (role.equals("manager")) isManager = true;
                System.out.println("Am I a manager? " + isManager);
                gridView = (GridView) findViewById(R.id.tableLayout);
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
            role = "user";
            System.out.println("User is Anonimus");


        } else {

            userid = user.getUid();
            System.out.println("userid=" + userid);}

            }


@Override
    public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.user_option, menu);

                return true;
            }
@Override
    public boolean onOptionsItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_user_list) {
                    //goToMyEventPage();
                    return true;
                } else if (id == R.id.menu_profile) {
                    //goToUserProfilePage();
                    return true;
                } else if (id == R.id.menu_logout) {
                    logout();
                    return true;
                }
                return super.onOptionsItemSelected(item);
            }

            public void logout() {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity_Firebase.class));
                finish();

                Toast.makeText(getApplicationContext(), "You Logout successfully", Toast.LENGTH_LONG).show();
            }

        }