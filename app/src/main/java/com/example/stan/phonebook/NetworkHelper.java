package com.example.stan.phonebook;

import android.content.Context;
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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stan on 10/4/2016.
 */
public class NetworkHelper {


    public static void updateField(String type, final String updateString, String uid, Context passedContext) {
        //Getting values from edit texts
        //loading = ProgressDialog.show(this,"Logging in...","Fetching...",false,false);
        //Creating a string request
        StringRequest stringRequest;
        final String userID = uid;
        final Context context = passedContext;
        switch(type) {

            case "mood":
                stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_MOOD,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(context ,response,Toast.LENGTH_LONG).show();



                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_UID, userID);
                        params.put(Config.KEY_MOOD, updateString);

                        //returning parameter
                        return params;
                    }
                };
                break;
            case "status":
                stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_STATUS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_UID, userID);
                        params.put(Config.KEY_STATUS, updateString);

                        //returning parameter
                        return params;
                    }
                };
                break;
            case "ci":
                stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_CI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context,response,Toast.LENGTH_LONG).show();


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_UID, userID);
                        params.put(Config.KEY_CI, updateString);

                        //returning parameter
                        return params;
                    }
                };
                break;
            default:
                stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_MOOD,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put(Config.KEY_UID, userID);
                        params.put(Config.KEY_MOOD, updateString);

                        //returning parameter
                        return params;
                    }
                };
                break;
        }

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public static void refreshList(String uid, Context passedContext){
        //Getting values from edit texts
        //loading = ProgressDialog.show(this,"Logging in...","Fetching...",false,false);

        final String userID = uid;

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
                            MainActivity.myInfo = list.remove(list.size()-1);
                            Collections.sort(list);
                            //retrievedContactDetailsList = list; // return when finished
                            ContactFragment.adapter.clear();
                            ContactFragment.mSwipeRefreshLayout.setRefreshing(false);
                            ContactFragment.adapter.addAll(list);
                            MainActivity.vpPager.getAdapter().notifyDataSetChanged();
                        } catch (IOException e1) {
                            e1.printStackTrace();
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
        RequestQueue requestQueue = Volley.newRequestQueue(passedContext);
        requestQueue.add(stringRequest);

    }


}
