package com.example.stan.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stan on 9/14/2016.
 */
public class FriendRequestAdapter extends BaseAdapter {

    // First initialize out values
    private Context context;
    private LayoutInflater inflater; // inflater
    private int resource = 0;   // The view
    private List<PendingFriends> pendingFriendsList; // our list of contacts
    //private List<MoreContactDetails> more_contact_list; // our list of extra info per contact

    private class ViewHolder {
        TextView NameTextView; // Name TextView
        TextView MobileTextView; // Mobile TextView
        ImageView image;        // Thumbnail ImageView
        ImageView favStar;      // Favorite Star
    }


    public FriendRequestAdapter(Context context, int resource, List<PendingFriends> pendingFriendsList) {

        // Constructor would get these values from its paramters and set them.
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.pendingFriendsList = pendingFriendsList;
        this.resource = resource;

    }

    public int getCount() {
        return pendingFriendsList.size();
    } // returns size of contact_list

    public PendingFriends getItem(int position) {
        return pendingFriendsList.get(position);
    } // Returns object in position

    public long getItemId(int position) {
        return position;
    } // returns position in contact_list

    public View getView(int position, View convertView, ViewGroup parent) { // Get the view, our main function
        ViewHolder holder = null;

        final int friendPosition = position;
        if(convertView == null) { // What this does is recycle our list views, which means that new lsit
            holder = new ViewHolder(); // objects are not replaced, but rather reused and changed. Of course
            // This has its own share of problems...
            // Check out the Slow Loading Images in the documentation.txt
            convertView = inflater.inflate(resource , null);
            holder.NameTextView = (TextView) convertView.findViewById(R.id.pending_friend_name); // add name line TextView to holder (firstLine)
            //holder.MobileTextView = (TextView) convertView.findViewById(R.id.secondLine); // add mobile line TextView to holder (secondLine)
            //holder.image = (ImageView) convertView.findViewById(R.id.profilePic); // add Image ImageView to holder (profilePic)
            convertView.setTag(holder);
           // holder.favStar = (ImageView) convertView.findViewById(R.id.favoriteStar);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.NameTextView.setText(pendingFriendsList.get(position).getName()); // set Name

        //Handle buttons and add onClickListeners
        Button rejectBtn = (Button)convertView.findViewById(R.id.reject_button);
        Button acceptBtn = (Button)convertView.findViewById(R.id.accept_button);
        //holder.MobileTextView.setText(contact_list.get(position).getPhone().getMobile()); // set Number
        //holder.image.setImageResource(R.drawable.ic_launcher); // When an image is not loaded yet, set it to

        rejectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                respondToFriendRequest(friendPosition, false);
                pendingFriendsList.remove(pendingFriendsList.get(friendPosition));
                notifyDataSetChanged();
            }
        });
        acceptBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                respondToFriendRequest(friendPosition, true);
                pendingFriendsList.remove(pendingFriendsList.get(friendPosition));
                notifyDataSetChanged();
            }
        });
        // a stock image icon
        // new LoadImage(holder.image).execute(contact_list.get(position).getSmallImageURL());
        // Here's where is gets tricky. We're running our own Asynchrous task called LoadImage that reads
        // in an image from an URL (in this case the contact's Small Image URL). It is then set to the
        // thumbnail image here in the ImageView.

        // Make sure to check the Slow Loading Image section in the documentation to see some of the issues
        // That are arriving when we use this. Images are just not loading efficiently enough.

//        if(more_contact_list.get(position).isFavorite()) {
//
//            Drawable myStar = context.getResources().getDrawable(R.drawable.ic_star_border_black_36dp);
//            holder.favStar.setImageDrawable(myStar);
//        } else {
//
//            Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
//            holder.favStar.setImageDrawable(transparentDrawable);
//        }

        return convertView; // of course, return the view
    }

    private void respondToFriendRequest(int position, boolean accepted){
        //Getting values from edit texts
        final String didAccept = String.valueOf(accepted);
        final String acceptCode = String.valueOf(pendingFriendsList.get(position).getAcceptCode());
        //final String password = PasswordET.getText().toString().trim();

        //loading = ProgressDialog.show(getActivity(),"Logging in...","Fetching...",false,false);

        //Creating a string request
        StringRequest confirmFriendStringRequest = new StringRequest(Request.Method.POST, Config.CONFIRM_FRIEND_URL,
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
                }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_ACCEPTED, didAccept);
                params.put(Config.KEY_ACCEPT_CODE, acceptCode);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(confirmFriendStringRequest);
    }
}