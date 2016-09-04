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


    public void facebook(View view) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/hatachana.lod"));
        startActivity(myIntent);
    }

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0507522470"));
        startActivity(intent);
    }

    public void mail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ymlod@mtova.org.il","michaelm@mtova.org.il",
                "dvirma@jdcnet.org","madainbal@gmail.com","y.taasuka@mtova.org.il","idan300@gmail.com",
                "tigist@mtova.org.il"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Message from app");
        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
