package com.goodthinking.younglod.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.goodthinking.younglod.user.R;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private AdapterOne adapterOne;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.tableLayout);
        adapterOne = new AdapterOne(getApplicationContext());
        gridView.setAdapter(adapterOne);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(MainActivity.this,ComingSoon.class);
                startActivity(intent);
            }


            });}}