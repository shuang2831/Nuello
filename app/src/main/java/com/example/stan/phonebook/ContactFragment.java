package com.example.stan.phonebook;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
 *
 * Created by Stan on 1/30/2016.
 *
 * ContactDetails is the Main List Fragment, which means that when the app is started,
 * the onCreate function (commands executed upon app creation) will create an instance
 * of this fragment and overlay our fragment container (activity_main.xml).
 *
 * The purpose of this fragment is to create a list of contacts that, when one of
 * the contacts are clicked, will call function openContactInfo and create an instance
 * of a new InfoFragment fragment to take over the fragment container.
 *
 * This fragment uses the view fragment_contact_list.xml for its list and the view
 * fragment_contact_list_item.xml for its contact list item.
 *
 * ContactFragment is the main list fragment that is inflated when the app is opened.
 * It displays a list (retrievedUserInfoList) in a recyclerview that contains the user's
 * friends moods, availability, and names. This list is held in a CustomAdapter (adapter)
 * which handles what is displayed in the view.
*
*
*/


public class ContactFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rView;
    private String userID;

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;

    static CustomAdapter adapter;

    protected RecyclerView.LayoutManager mLayoutManager;
    static SwipeRefreshLayout mSwipeRefreshLayout;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    OnContactSelectedListener mCallback;

    List<UserInfo> retrievedUserInfoList; // Initialize a list of class ContactDetails
                                                    // Because the JSON used contains an array of
                                                    // contacts, we need to pull out a full list,
                                                    // not just a single contact.
   // List<MoreContactDetails> MoreContactDetailsList; // Initialize a list of the class MoreContactDetails
                                                    // Which contains objects that have more date for each
                                                    // contact, pulled from their respective DetailsURL
                                                    // Check out MoreContactDetails.Class for more details


    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnContactSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void openContactInfo(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(Config.UID_SHARED_PREF, "0");
        View view =inflater.inflate(R.layout.fragment_contact_list, container, false); // Set our fragment_contact_list.xml as our view (list view)


        retrievedUserInfoList = MainActivity.FriendDetailList; // pull in the ContactDetails list from MainActivity's call of RetrieveContactDetails


        adapter = new CustomAdapter(getActivity(), // Here we use our CustomAdapter to set up our list items.
                                                    // As you can see, fragment_contact_list_item.xml is being used
                                                    // as our list item, while retrievedContactDetailsList is the list
                                                    // of objects we are pulling values from.
                                                    // Check out CustomAdapter.Java for more info

                R.layout.fragment_contact_list_item, retrievedUserInfoList);//, MoreContactDetailsList);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int verticalSpacing = 10;
        VerticalSpaceItemDecorator itemDecorator =
                new VerticalSpaceItemDecorator(verticalSpacing);
        ShadowVerticalSpaceItemDecorator shadowItemDecorator =
                new ShadowVerticalSpaceItemDecorator(getActivity(), R.drawable.drop_shadow);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        rView = (RecyclerView)view.findViewById(R.id.listings_view);

        rView.setLayoutManager(layoutManager);
        rView.addItemDecoration(shadowItemDecorator);
        rView.addItemDecoration(itemDecorator);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                retrievedUserInfoList.clear();
                NetworkHelper.refreshList(userID, getActivity());

            }
        });
        rView.setAdapter(adapter); // set it as our list adapter
        mSwipeRefreshLayout.setRefreshing(false);
        return view;            // return the view (we made a list woooo!)
    }


    @Override
    public void onAttach(Activity activity) {  // This just makes sure that the container activity
        super.onAttach(activity);               // is implemented. Looks like something is depreciated
                                                // though. I should look at that later.
        try {
            mCallback = (OnContactSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnContactSelectedListener");
        }
    }

//    public static void refreshList(){
//        //Getting values from edit texts
//        //loading = ProgressDialog.show(this,"Logging in...","Fetching...",false,false);
//
//        //Creating a string request
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.RETRIEVE_MOODS_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        List<UserInfo> list;
//                        ObjectMapper mapper = new ObjectMapper();
//                        try {
//                            list = mapper.readValue(response, new TypeReference<List<UserInfo>>() { // use the mapper to read values
//                            });
//                            MainActivity.myInfo = list.remove(list.size()-1);
//                            Collections.sort(list);
//                            //retrievedContactDetailsList = list; // return when finished
//                            adapter.clear();
//                            mSwipeRefreshLayout.setRefreshing(false);
//                            adapter.addAll(list);
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //You can handle error here if you want
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> params = new HashMap<>();
//                //Adding parameters to request
//                params.put(Config.KEY_UID, userID);
//
//                //returning parameter
//                return params;
//            }
//        };
//
//        //Adding the string request to the queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);


        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.clear();
                adapter.setFilter(newText);
                //adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );
    }


}