package com.example.stan.phonebook;

/**
 * Created by Belal on 11/14/2015.
 */
public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://192.168.1.5/login.php";
    public static final String REGISTER_URL = "http://192.168.1.5/register.php";
    public static final String RETRIEVE_MOODS_URL = "http://192.168.1.5/getData.php";
    public static final String ADD_FRIEND_URL = "http://192.168.1.5/addFriends.php";
    public static final String RETRIEVE_FRIEND_REQUESTS_URL = "http://192.168.1.5/getFriendRequests.php";
    public static final String CONFIRM_FRIEND_URL = "http://192.168.1.5/confirmFriend.php";
    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_UID = "uid";
    public static final String KEY_NAME = "name";
    public static final String KEY_ACCEPTED = "accepted";


    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String UID_SHARED_PREF = "uid";
    public static final String USERNAME_SHARED_PREF = "username";
    public static final String NAME_SHARED_PREF = "name";

    public static final String LOGIN_JSON_ARRAY = "login_response";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}