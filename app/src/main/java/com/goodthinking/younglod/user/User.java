package com.goodthinking.younglod.user;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by user on 23/07/2016.
 */
@IgnoreExtraProperties
public class User implements Serializable {

    private String UserName;
    private String UserPhone;
    private String UserEmail;
    private String UserPassword;
    private int UserNoOfParticipators;
    private String Role; // if manager than
    @Exclude
    private String UserID;

    public User(String userName, String userPhone, String userEmail, String userPassword) {
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
        UserPassword = userPassword;
    }

    public User() {
    }

     public User(String userName, String userPhone, String userEmail, int userNoOfParticipators) {
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
        UserNoOfParticipators = userNoOfParticipators;
    }

    @Exclude
    public String getUserID() {
        return UserID;
    }

    @Exclude
    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
    public int getUserNoOfParticipators() {
        return UserNoOfParticipators;    }

    public void setUserNoOfParticipators(int userNoOfParticipators) {
        UserNoOfParticipators = userNoOfParticipators;
    }

    @Exclude
    public HashMap<String, Object> UsertoMap() {
        HashMap<String, Object> userresult = new HashMap<>();
        userresult.put("UserName", UserName);
        userresult.put("UserPhone", UserPhone);
        userresult.put("UserEmail", UserEmail);
        userresult.put("UserPassword", UserPassword);

        return userresult;
    }
    @Exclude
    public HashMap<String, Object> ApplicanttoMap() {
        HashMap<String, Object> userresult = new HashMap<>();
        userresult.put("UserName", UserName);
        userresult.put("UserPhone", UserPhone);
        userresult.put("UserEmail", UserEmail);
        userresult.put("UserNoOfParticipators",UserNoOfParticipators);

        return userresult;
    }

}
