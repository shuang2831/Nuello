
package com.example.stan.phonebook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * Created by Stan on 1/30/2016.
 * This is where we are building up our contact details page. When a contact is clicked on the initial list,
 * And instance of this fragment will be made and displayed. The info here will be things such as:
 *
 * Name
 * Company
 * Birthday
 * Phone numbers (home, work, mobile)
 * Email
 * A nice big picture
 *
 */

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    int mutedColor;
    private int PICK_IMAGE_REQUEST = 1;
    private NetworkImageView propic;
    private ImageLoader imageLoader;

    private ImageView moodPng;
    private TextView availability;
    private TextView currentlyInto;

    private Bitmap bitmap;
    private String userID;
    private String name;

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    List<UserInfo> if_RetrievedContactDetailsList; // Initialize a list of class ContactDetails
    UserInfo myProfileInfo;
    // Because the JSON used contains an array of
    // contacts, we need to pull out a full list,
    // not just a single contact.
    // Just like the cone in ContactFragment
    // "if_" stands for InfoFragment, but I know that can get confusing. I should change it...

    List<MoreContactDetails> if_MoreContactDetailsList; // Initialize a list of the class MoreContactDetails
    // Which contains objects that have more date for each
    // contact, pulled from their respective DetailsURL
    // Check out MoreContactDetails.Class for more details


//    public static InfoFragment newInstance(String position) {
//        InfoFragment fragment = new InfoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_POSITION, position);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(Config.UID_SHARED_PREF, "0");
        name = sharedPreferences.getString(Config.NAME_SHARED_PREF, "???");

        if_RetrievedContactDetailsList = MainActivity.FriendDetailList; // Again like in ContactFragment, is this a good idea?
        myProfileInfo = MainActivity.myInfo;
        //if_MoreContactDetailsList = MainActivity.MoreContactDetailsList; // Pull out this date from MainActivity. I hope this isn't making things hella slow

//        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION); // Save the position
//        }
        mCurrentPosition = getArguments().getInt("position");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.change_image);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //uploadImage();
                showFileChooser();
            }
        });

        propic = (NetworkImageView) view.findViewById(R.id.header);
        imageLoader = LoadImage.getInstance(getActivity().getApplicationContext())
                .getImageLoader();
        imageLoader.get(myProfileInfo.getPropicLocation(), ImageLoader.getImageListener(propic,
                R.drawable.ic_launcher, android.R.drawable
                        .ic_dialog_alert));
        propic.setImageUrl(myProfileInfo.getPropicLocation(), imageLoader);

        availability = (TextView) view.findViewById(R.id.status_profile);
        currentlyInto = (TextView) view.findViewById(R.id.currently_into_profile);
        moodPng = (ImageView) view.findViewById(R.id.mood_profile);

        switch(myProfileInfo.getMood()){

            case "happy": moodPng.setImageResource(R.drawable.happy);
                break;
            case "sad": moodPng.setImageResource(R.drawable.sad);
                break;
            case "neutral": moodPng.setImageResource(R.drawable.neutral);
                break;
            default: moodPng.setImageResource(R.drawable.neutral);
                break;
        }
        availability.setText(myProfileInfo.getStatus());
        currentlyInto.setText(myProfileInfo.getTopic());


        //ImageView header = (ImageView) view.findViewById(R.id.header);


        // Inflate the layout for this fragment

        return view; // return view
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                //propic.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {

            // Set Contact Details based on arguments (position)
            updateContactDeats(args.getInt(ARG_POSITION));

        } else if (mCurrentPosition != -1) {
            // Set Contact Details based on saved instance state defined during onCreateView
            updateContactDeats(mCurrentPosition);
        }
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_IMAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                        getMyData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(Config.KEY_IMAGE, image);
                params.put(Config.KEY_UID, userID);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void getMyData() {
        //Getting values from edit texts
        //loading = ProgressDialog.show(this,"Logging in...","Fetching...",false,false);
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_MY_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //,,UserInfo myInfo;
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            MainActivity.myInfo = mapper.readValue(response.substring(1, response.length() - 1), UserInfo.class); // use the mapper to read values
                            propic = (NetworkImageView) getView().findViewById(R.id.header);
                            myProfileInfo = MainActivity.myInfo;
                            imageLoader = LoadImage.getInstance(getActivity().getApplicationContext())
                                    .getImageLoader();
                            imageLoader.get(myProfileInfo.getPropicLocation(), ImageLoader.getImageListener(propic,
                                    R.drawable.ic_launcher, android.R.drawable
                                            .ic_dialog_alert));
                            propic.setImageUrl(myProfileInfo.getPropicLocation(), imageLoader);
                            //,,Collections.sort(list);
                            //FriendDetailList = list; // return when finished
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_UID, userID);

                //returning parameter
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void updateContactDeats(int position) {

        // This big hunk of code should be straightforward. It sets the Textviews and images in the
        // Contact Details view. We pull our data from both if_RetrievedContactDetailsList and
        // MoreContactDetailsList in order to do so.
//
//        ImageView bigImage = (ImageView) getActivity().findViewById(R.id.bigProfilePic);
//        new LoadImage(bigImage).execute(if_MoreContactDetailsList.get(position).getLargeImageURL()); // Set big image
//                                                                                                // Check LoadImage.java for more details
//                                                                                                // Issue, it takes so long to load
//        TextView employeeName = (TextView) getActivity().findViewById(R.id.nameLine); // Set name
//        employeeName.setText(if_RetrievedContactDetailsList.get(position).getName());

//        TextView companyName = (TextView) getActivity().findViewById(R.id.company); // Set company
//        companyName.setText(if_RetrievedContactDetailsList.get(position).getCompany());
//
//        TextView streetAddress = (TextView) getActivity().findViewById(R.id.street); // Set street
//        streetAddress.setText(if_MoreContactDetailsList.get(position).getAddress().getStreet());
//
//        TextView cityStateZip = (TextView) getActivity().findViewById(R.id.city_state_zip); // Set city-state-zip
//        cityStateZip.setText(if_MoreContactDetailsList.get(position).getAddress().getCity() + ", "
//                + if_MoreContactDetailsList.get(position).getAddress().getState() + " "
//                + if_MoreContactDetailsList.get(position).getAddress().getZip());
//
//        TextView emailAddress = (TextView) getActivity().findViewById(R.id.email); // Set E-Mail
//        emailAddress.setText(if_MoreContactDetailsList.get(position).getEmail());
//
//        TextView work_num = (TextView) getActivity().findViewById(R.id.phone_work); // Set Work Number
//        work_num.setText(getResources().getString(R.string.label_work) + "     " + if_RetrievedContactDetailsList.get(position).getPhone().getWork());
//
//        TextView home_num = (TextView) getActivity().findViewById(R.id.phone_home); // Set Home Number
//        home_num.setText(getResources().getString(R.string.label_home) + "   " + if_RetrievedContactDetailsList.get(position).getPhone().getHome());
//
//        TextView mobile_num = (TextView) getActivity().findViewById(R.id.phone_mobile); // Set Mobile Number
//        mobile_num.setText(getResources().getString(R.string.label_mobile) + "  " + if_RetrievedContactDetailsList.get(position).getPhone().getMobile());
//
//        TextView birthdate = (TextView) getActivity().findViewById(R.id.birthdate); // Set Birthday
//        birthdate.setText(getDateFromUnix(if_RetrievedContactDetailsList.get(position).getBirthdate()));


        mCurrentPosition = position; // Update position

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current contact position in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public String getDateFromUnix(String unixDate){

        // This function is used for the birthday.
        // When we pull out the birthdate from the JSON URL, its given to use in a string
        // representing the Unix Stamp Time Format. This is a way to convert that into
        // a readable birthdat String.

        long l = Long.parseLong(unixDate);
        Date date = new Date(l*1000L); // convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM F, yyyy"); // Format: ex December 21, 1994 (Guess who just turned 21?)
        sdf.setTimeZone(TimeZone.getTimeZone("CST")); // Central TimeZone

        String formattedDate = sdf.format(date);
        return formattedDate;

    }


}