package com.goodthinking.youngloduser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.goodthinking.youngloduser.model.User;
import com.goodthinking.youngloduser.user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity_Firebase extends AppCompatActivity {
    private EditText userEmail, userPassword;
    String userPswtext, userEmailtext;
    private FirebaseAuth auth;
    private DatabaseReference Userdatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        Userdatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);


        if (auth.getCurrentUser() != null) {
            String UserID = auth.getCurrentUser().getUid();
            System.out.println("userid=" + UserID);
            if (auth.getCurrentUser().isAnonymous()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("UserName", "Anonymous");
                intent.putExtra("Role", "user");
                startActivity(intent);
                finish();
            } else loadUser(UserID);
        } else {
            setContentView(R.layout.activity_login_activity__firebase);
            userEmail = (EditText) findViewById(R.id.Login_email);
            userPassword = (EditText) findViewById(R.id.Login_password);
        }
    }

    private void loadUser(String userID) {
        Userdatabase.child("users").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String role = "user", name = "Anonymous";
                        if (dataSnapshot.getChildrenCount() > 0) {
                            System.out.println("children=" + dataSnapshot.getChildrenCount());
                            User newUser = dataSnapshot.getValue(User.class);
                            name = newUser.getUserName();
                            role = newUser.getRole();
                            if (role == null || role.length() == 0 || !role.equals("manager"))
                                role = "user";
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("UserName", name);
                        intent.putExtra("Role", role);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void login(View view) {
        userEmailtext = userEmail.getText().toString();
        userPswtext = userPassword.getText().toString();

        if (userPswtext.equals("") || userEmailtext.equals("")) {

            Toast.makeText(getApplicationContext(), R.string.Signup_message_fill_fields, Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage(getString(R.string.Progress_Dialog_login));
        progressDialog.show();

        auth.signInWithEmailAndPassword(userEmailtext, userPswtext).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_faild_message) + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    loadUser(task.getResult().getUser().getUid());

                }
                progressDialog.dismiss();
            }
        });

    }

    public void resetpassword(View view) {
        userEmailtext = userEmail.getText().toString();
        if (userEmailtext.equals("")) {

            Toast.makeText(getApplicationContext(), R.string.Forget_password, Toast.LENGTH_LONG).show();
            return;
        }


        auth.sendPasswordResetEmail(userEmailtext)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.InstructionsToResetPassword, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.ReSendEMail, Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    public void gotomainmenu(View view) {
        auth.signInAnonymously();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Role", "user");
        startActivity(intent);
    }

    public void gotoregisteration(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity_Firebase.class);
        intent.putExtra("Role", "user");
        startActivity(intent);
    }
}
