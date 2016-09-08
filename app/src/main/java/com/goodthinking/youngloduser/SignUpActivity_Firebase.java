package com.goodthinking.youngloduser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.goodthinking.youngloduser.user.R;
import com.goodthinking.youngloduser.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity_Firebase extends AppCompatActivity {
    private EditText userEmail, userPassword, UserName, UserPhone;
    String userID,userPswstr, userEmailstr, UserNamestr, UserPhonestr;
    private FirebaseAuth auth;
    private DatabaseReference Userdatabase;
    private String TAG = "signupActivity";
    private ProgressDialog progressDialog;
    String key;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity__firebase);

        sharedPref = getSharedPreferences("setting", MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        Userdatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        UserName = (EditText) findViewById(R.id.Signup_username);
        UserPhone = (EditText) findViewById(R.id.Signup_userphone);
        userEmail = (EditText) findViewById(R.id.Signup_email);
        userPassword = (EditText) findViewById(R.id.Signup_password);
    }

    public void register(View view) {
        UserNamestr = UserName.getText().toString();
        UserPhonestr = UserPhone.getText().toString();
        userEmailstr = userEmail.getText().toString();
        userPswstr = userPassword.getText().toString();
        if (userPswstr.equals("") || userEmailstr.equals("")
                || UserNamestr.equals("") || UserPhonestr.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.Signup_message_fill_fields, Toast.LENGTH_LONG).show();
            return;
        }
        if (userPswstr.length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.Message_invalid_password, Toast.LENGTH_LONG).show();
            userPassword.setError(getString(R.string.errorset_password));
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmailstr).matches()) {
            Toast.makeText(getApplicationContext(), R.string.Message_invalid_Email, Toast.LENGTH_LONG).show();
            userEmail.setError(getString(R.string.errorset_email));
            return;
        }
        progressDialog.setMessage(getString(R.string.Progress_Dialog_Register));
        progressDialog.show();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", userEmail.getText().toString());
        editor.apply();
        editor.commit();

        auth.createUserWithEmailAndPassword(userEmailstr, userPswstr).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(),
                                Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.register_faild_message) + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.Registration_successful) + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            //creatNewUser(task.getResult().getUser());
                            creatNewUser();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("UserName", UserNamestr);
                            intent.putExtra("Role", "youngloduser");
                            startActivity(intent);
                            finish();
                        }
                        progressDialog.dismiss();
                    }
                });



    }
    private void creatNewUser (){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        User Newuser = new User(UserNamestr,UserPhonestr,userEmailstr,userPswstr);
        Newuser.setUserID(userID);
        //Map<String,Object> usernew =new HashMap<>();
        //usernew.put(userID,Newuser.UsertoMap());
        Userdatabase.child("users").child((userID)).setValue(Newuser.UsertoMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.register_faild_message),
                            Toast.LENGTH_SHORT).show();;
                } else {
                    return;
                }

            }
        });
    }
}
