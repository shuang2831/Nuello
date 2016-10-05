package com.example.stan.phonebook;

/**
 * Created by Stan
 *
 * The Config file simply contains a series of literals that are used throughout the App's code.
 * The main purpose of this is to avoid the use of ambiguous strings and make the code more
 * readable.
 *
 */
public class Config {
    // URL to our PHP files to communicate with our MySQL Database
    public static final String UPLOAD_IMAGE_URL = "http://10.0.0.9//uploadImage.php";
    public static final String LOGIN_URL = "http://10.0.0.9//login.php";
    public static final String REGISTER_URL = "http://10.0.0.9//register.php";
    public static final String RETRIEVE_MOODS_URL = "http://10.0.0.9//getData.php";
    public static final String GET_MY_DATA_URL = "http://10.0.0.9//getMyData.php";
    public static final String ADD_FRIEND_URL = "http://10.0.0.9//addFriends.php";
    public static final String RETRIEVE_FRIEND_REQUESTS_URL = "http://10.0.0.9//getFriendRequests.php";
    public static final String CONFIRM_FRIEND_URL = "http://10.0.0.9//confirmFriends.php";
    public static final String UPDATE_MOOD = "http://10.0.0.9//updateMood.php";
    public static final String UPDATE_STATUS = "http://10.0.0.9//updateStatus.php";
    public static final String UPDATE_CI = "http://10.0.0.9//updateInterests.php";

    // Keys for various database fields we would take from
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_UID = "uid";
    public static final String KEY_NAME = "name";
    public static final String KEY_ACCEPTED = "accepted";
    public static final String KEY_ACCEPT_CODE = "accept_code";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_MOOD = "mood";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CI = "ci";

    // If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    // Keys for Sharedpreferences
    // This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myApp";

    // These strings would be used to store/catagorize the shared preferences we need to save
    // to the device
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String UID_SHARED_PREF = "uid";
    public static final String USERNAME_SHARED_PREF = "username";
    public static final String NAME_SHARED_PREF = "name";
    public static final String LOGIN_JSON_ARRAY = "login_response";
    public static final String MOOD_SHARED_PREF = "mood";
    public static final String STATUS_SHARED_PREF = "status";
    public static final String PROPIC_SHARED_PREF = "propic";
    public static final String CI_SHARED_PREF = "propic";

    // We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}