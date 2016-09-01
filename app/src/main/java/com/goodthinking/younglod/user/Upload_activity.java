package com.goodthinking.younglod.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Upload_activity extends AppCompatActivity {
    private ImageView img;

    Context ctx = this;
    private DatabaseReference mDatabase;
    private Spinner spinner;

    ArrayList<String> files = new ArrayList<String>();
    ArrayList<Integer> fileId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        img = (ImageView) findViewById(R.id.img);

    }

    protected void onStart() {
        super.onStart();
        spinner = (Spinner) findViewById(R.id.spinner);
        Field[] fields = R.raw.class.getFields();
        for (int count = 1; count < fields.length; count++) {
            files.add(fields[count].getName());
            try {
                Log.i("Raw Asset: ", fields[count].getName() + " " + fields[count].getInt(fields[count]));
                fileId.add(fields[count].getInt(fields[count]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,0,files);
        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, R.layout.simple_spinner_dropdown, files);
        spinner.setAdapter(adp);

        Button button = (Button) findViewById(R.id.upload);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Button Clicked", Toast.LENGTH_SHORT).show();
                upload(view);
            }

        });

    }

    public void upload(View v) {
        int pos = spinner.getSelectedItemPosition();
        Log.d("position", "" + pos);
        int id = fileId.get(pos);
        Log.d("id", "" + id);

        InputStream inputStream = null;
        try {
            inputStream = ctx.getResources().openRawResource(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = "", line = "";
        Reader reader = null;
        try {
            reader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(reader);
        try {
            while ((line = br.readLine()) != null) {
                str += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Toast.makeText(ctx, "str="+str, Toast.LENGTH_LONG).show();
        Map<String, Object> obj = null;
        JSONObject jstr = null;

        try {
            jstr = new JSONObject(str);
            obj = jsonToMap(jstr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseReference amutot = null;
        amutot = mDatabase.getRoot().child("Tables");
        amutot.updateChildren(obj); // update/create table

    }

    public Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            //System.out.println("class="+value.getClass());
            if (value instanceof String)     // check if it has reference to drawable
            {
                if (((String) value).startsWith("@drawable")) {
                    String file = ((String) value).substring(1);
                    System.out.println("value=" + ((String) value).substring(1));
                    value = getPicture(((String) value).substring(1));
                    if (value == null)
                        Toast.makeText(Upload_activity.this, "file=" + file + " not found", Toast.LENGTH_LONG).show();
                }
            }
            if (value != null) map.put(key, value);
        }
        return map;
    }

    public Map<String, Object> toList(JSONArray array) throws JSONException {
        Map<String, Object> map_list = new HashMap<String, Object>();
        for (int i = 0; i < array.length(); i++) {
            String key = UUID.randomUUID().toString();
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            //System.out.println("class="+value.getClass());

            if (value instanceof String)     // check if it has reference to drawable
            {
                if (((String) value).startsWith("@drawable/")) {
                    System.out.println("value=" + ((String) value).substring(1));
                    value = getPicture(((String) value).substring(1));

                }
            }
            map_list.put(key, value);
        }
        return map_list;
    }

    private int getResId(String resName) {
        int defId = -1;
       /* try {
            Field f = R.drawable.class.getDeclaredField(resName);
            Field def = R.drawable.class.getDeclaredField("transparent_flag");
            defId = def.getInt(null);
            return f.getInt(null);
        } catch (NoSuchFieldException e) {
            return defId;
        }
        catch (IllegalAccessException e) {
            return defId;
        }*/
        int ress = -1;
        try {
            ress = this.getResources().
                    getIdentifier(resName, null, this.getPackageName());
        } catch (Exception e) {
            ress = -1;
        }
        return ress;
    }

    public String getPicture(String file) {


        int resid = getResId(file);

        if (resid == -1 || resid == 0) {
            System.out.println("failed to open " + file + " file");
            return null;
        }
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resid);//getting my image

        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE); //compressing my image
        bmp.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.NO_WRAP);    // convert it to String
        Log.d(getClass().getName(), "before opening image");
        return imageFile;
    }

    public static int count = 1;
/*

    public void getit(View v) {
        Log.d(getClass().getName(), "start of getit");

        mDatabase.child("Tables").child("words").addListenerForSingleValueEvent(new ValueEventListener() {
            // reading another image
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("number=" + dataSnapshot.getChildrenCount());
                int i = 0;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Words word = new Words();
                    word = snap.getValue(Words.class);
                    word.setKey(snap.getKey());
                    System.out.println("key=" + word.getKey() + " word=" + word.getWord() + " nword=" + word.getNword());

                    String image = word.getImg();
                    i++;
                    if (image == null || image.length() == 0) continue;
                    System.out.println("word=" + word.getWord() + " nword=" + word.getNword());
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    System.out.println(decodedString.length);
                    img.setImageBitmap(decodedByte);
                    Log.d(getClass().getName(), "picture fruit");
                    if (i == count++) break;
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
*/
}

