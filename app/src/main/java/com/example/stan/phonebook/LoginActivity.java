package com.example.stan.phonebook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * The LoginActivity handles the logic and functionality of the login view a user sees when they
 * open up the app for the first time, or are not already logged in (through sharedPreferences).
 *
 * The LoginActivity needs to do a few things:
 *
 *  1. Allow the user to enter a username and password
 *  2. Send a network request to confirm the user's credentials
 *  3. Store the user's initial info (uid, username, etc) into sharedPreferences
 *  4. Allow the user to register an account if they do not have one already, starting the
 *      RegisterActivity
 *
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Defining view elements (buttons, text fields, etc)
    private EditText UsernameET;
    private EditText PasswordET;
    private AppCompatButton buttonLogin;
    private TextView buttonRegister;
    private ProgressDialog loading;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //Initializing edit text view elements, login button, register text
        UsernameET = (EditText) findViewById(R.id.et_username);
        PasswordET = (EditText) findViewById(R.id.et_password);
        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);
        buttonRegister = (TextView) findViewById(R.id.linkSignup);

        //Adding click listener for login button
        buttonLogin.setOnClickListener(this);

        //Adding click listener for register text
        buttonRegister.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This login function uses a volley network request in order to send a user's entered
     * username and password through a php call (login.php) and return whether or not it
     * is a success, as well as some info about that user (uid, email, etc)
     */
    private void login(){
        //Getting values from edit texts
        final String username = UsernameET.getText().toString().trim();
        final String password = PasswordET.getText().toString().trim();
        // Show loading progress
        loading = ProgressDialog.show(this,"Logging in...","Fetching...",false,false);

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // initialize blank variables
                        String name = "";
                        String uid = "";
                        String email = "";
                        String accepted = "";
                        try {
                            // We create a json object and convert our network response into that
                            // json object.
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray login_response = jsonObject.getJSONArray(Config.LOGIN_JSON_ARRAY);
                            JSONObject loginData = login_response.getJSONObject(0);
                            name = loginData.getString(Config.KEY_NAME);
                            email = loginData.getString(Config.KEY_EMAIL);
                            uid = loginData.getString(Config.KEY_UID);
                            accepted = loginData.getString(Config.KEY_ACCEPTED);
                        }catch (JSONException e) {
                            e.printStackTrace();
                            loading.dismiss();
                        }
                        //If we are getting success from server
                        if(accepted.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values from the retrieved json to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, email);
                            editor.putString(Config.UID_SHARED_PREF, uid);
                            editor.putString(Config.NAME_SHARED_PREF, name);
                            editor.putString(Config.USERNAME_SHARED_PREF, username);

                            //Saving values to editor
                            editor.commit();

                            //Starting MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * Starts the RegisterActivity
     */
    private void register(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        // Calling the login function if login button is clicked
        if (v == buttonLogin) {
            login();
        }
        // Calling the register function if register text is clicked
        if (v == buttonRegister) {
            register();
        }
    }
}
