package com.goodthinking.younglod.user;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by user on 23/07/2016.
 */
public class User implements Serializable {

    private String UserName;
    private String UserPhone;
    private String UserEmail;
    private String UserPassword;
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

    public User(String userName, String userPhone, String userEmail) {
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
    }

    @Exclude
    public String getUserID() {
        return UserID;
    }

    @Exclude
    public void setUserID(String userID) {
        UserID = userID;
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

        return userresult;
    }

}
