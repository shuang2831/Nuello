package com.example.stan.phonebook;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 *
 * Created by Stan on 1/30/2016.
 *
 * Here it is, our main activity! So this is the activity that is called upon opening the app.
 * What is does upon being made is pull out data from the JSON file using a couple of Asynchronous tasks.
 * I'm not totally sure if that's dangerous or foolish (or both), but I'll look into it when I get
 * the chance.
 *
 * We also set up our first fragment to be the initial view here, and write a function that changes that
 * view to our InfoFragment (fragment_contact_info) view.
 *
 * Essentially, MainActivity handles the fragment container (activity_main.xml)
 *
 */


public class MainActivity extends AppCompatActivity
        implements ContactFragment.OnContactSelectedListener {

    // ew, static variables, but they get the job done for now. Given more time I'd find a way to replace them
    static List<UserInfo> FriendDetailList; // List of contacts and their base info
    //static List<MoreContactDetails> MoreContactDetailsList = new ArrayList<MoreContactDetails>(); // List of contacts' extra info
    FragmentPagerAdapter adapterViewPager;
    private String userID;
    ViewPager vpPager;
    private SearchView mSearchView;
    private String mSearchString;


    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(Config.UID_SHARED_PREF, "0");

        refresh(true);

        //            //ContactDetailsList = new RetrieveContacts().execute().get(); // Pull out a list of Contacts (and their data; check out ContactDetails.class)
//
//            for(int numContacts = 0; numContacts < ContactDetailsList.size(); numContacts++){
//
//                //MoreContactDetailsList.add(numContacts, new RetrieveContactsMoreDetails().execute(ContactDetailsList.get(numContacts).getDetailsURL()).get());
//                // in a for loop (I'm wondering if this is suitable...), pull out the extra details from each contact using the DetailsURL of each one,
//                // and put it into a list "MoreContactDetailsList"
        //refresh();
//

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main); // Setting up the toolbar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        TabLayout tabs = (TabLayout) findViewById(R.id.tab_selector);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                openFriendFunctions();
////                Snackbar.make(view, "For the Future: Add Contacts!", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
//        vpPager.setAdapter(adapterViewPager);
        // Give the TabLayout the ViewPager
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
//        if (findViewById(R.id.fragment_container) != null) {
//
//            // Create an instance of ContactFragment
//            ContactFragment firstFragment = new ContactFragment();
//
//            // Pass extras as arguments
//            firstFragment.setArguments(getIntent().getExtras());
//
//            // Add the fragment to the 'fragment_container' FrameLayout
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, firstFragment).commit();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        //final Toast toast = new Toast(mApp);
//
//        if (mSearchView != null )
//        {
//            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            mSearchView.setIconifiedByDefault(false);
//
//            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
//            {
//                public boolean onQueryTextChange(String newText)
//                {
//                    mSearchString = newText;
//                    //doFilterAsync(mSearchString);
//                    //toast.makeText(getApplicationContext(), "Test1", Toast.LENGTH_LONG).show();
//                    return true;
//                }
//
//                public boolean onQueryTextSubmit(String query)
//                {
//                    mSearchString = query;
//                    //doFilterAsync(mSearchString);
//                    //toast.makeText(getApplicationContext(), "Test2", Toast.LENGTH_LONG).show();
//
//                    return true;
//                }
//            };
//
//            mSearchView.setOnQueryTextListener(queryTextListener);
//        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            // This causes the app to exit when the back arrow is pressed.
            // There are probably better uses for that, would be something to
            // expand on in the future
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openContactInfo(int position) {
        // The user selected a contact form the list!
        //vpPager.setCurrentItem(4);
        // Create fragment and give it an argument for which contact was selected
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {

            InfoFragment newFragment = new InfoFragment();
            Bundle args = new Bundle();
            args.putInt(InfoFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace current fragment in the fragment_container view (ContactFragment) with this fragment (InfoFragment),
            // and add the transaction to the back stack
            transaction.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            transaction.replace(R.id.fragment_container, newFragment, "detailFragment");
            transaction.addToBackStack(null);

            // Commit the transaction (bring up the new view)
            transaction.commit();
        }
    }
    public void openFriendFunctions() {
        // The user selected a contact form the list!

//        // Create fragment and give it an argument for which contact was selected
//        FriendFragment newFragment = new FriendFragment();
//        Bundle args = new Bundle();
////        args.putInt(InfoFragment.ARG_POSITION, position);
////        newFragment.setArguments(args);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        // Replace current fragment in the fragment_container view (ContactFragment) with this fragment (InfoFragment),
//        // and add the transaction to the back stack
//        transaction.setCustomAnimations(android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
//        transaction.replace(R.id.fragment_container, newFragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction (bring up the new view)
//        transaction.commit();

    }

    public void sendRefreshNotice(){


    }

    public void refresh(boolean firstRun){
        //Getting values from edit texts
        //loading = ProgressDialog.show(this,"Logging in...","Fetching...",false,false);
        final boolean firstRunx = firstRun;
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.RETRIEVE_MOODS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<UserInfo> list;
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            list = mapper.readValue(response, new TypeReference<List<UserInfo>>() { // use the mapper to read values
                            });
                            Collections.sort(list);
                            FriendDetailList = list; // return when finished
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        if(firstRunx == true) {
//                            if (findViewById(R.id.fragment_container) != null) {
//
//                                // Create an instance of ContactFragment
//                                ContactFragment firstFragment = new ContactFragment();
//
//                                // Pass extras as arguments
//                                firstFragment.setArguments(getIntent().getExtras());
//
//                                // Add the fragment to the 'fragment_container' FrameLayout
//                                getSupportFragmentManager().beginTransaction()
//                                        .add(R.id.fragment_container, firstFragment).commit();
//                            }
                            vpPager.setAdapter(adapterViewPager);
                            TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                            tabLayout.setupWithViewPager(vpPager);
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
                params.put(Config.KEY_UID, userID);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void onInfoFragmentInteraction(String string) {
        // Do stuff
    }

    public void onFriendFragmentInteraction(String string) {
        // Do different stuff
    }
}
