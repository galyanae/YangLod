package com.goodthinking.younglod.user;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    public void toWeb(View view) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.younglod.com/"));
        startActivity(myIntent);
}


}
