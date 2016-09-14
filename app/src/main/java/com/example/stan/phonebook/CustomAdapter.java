package com.example.stan.phonebook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Collection;
import java.util.List;

/**
 * Created by Stan on 1/30/2016.
 *
 * Here it is, my custom adapter. This is the first time writing my own, exciting!
 *
 * So what this Adapter does is take in 2 parameters:
 *
 *  1. The list item view: in this case fragment_contact_list_item.xml (resource)
 *  2. A list of ContactDetail objects: it's where we get our data (contact_list)
 *
 *  It then sets into each list item 3 values in the view : the name, mobile number, and thumbnail image
 *  Afterwards, we have our list!
 */
public class CustomAdapter extends BaseAdapter {

    // First initialize out values
    private Context context;
    private LayoutInflater inflater; // inflater
    private int resource = 0;   // The view
    private List<UserInfo> contact_list; // our list of contacts
    //private List<MoreContactDetails> more_contact_list; // our list of extra info per contact

    private class ViewHolder {
        TextView NameTextView; // Name TextView
        TextView MobileTextView; // Mobile TextView
        ImageView image;        // Thumbnail ImageView
        ImageView favStar;      // Favorite Star
    }

    public CustomAdapter(Context context, int resource, List<UserInfo> contact_list){//, List<MoreContactDetails> more_contact_list) {

        // Constructor would get these values from its paramters and set them.
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.contact_list = contact_list;
        //this.more_contact_list = more_contact_list;
        this.resource = resource;

    }

    public int getCount() {
        return contact_list.size();
    } // returns size of contact_list

    public UserInfo getItem(int position) {
        return contact_list.get(position);
    } // Returns object in position

    public long getItemId(int position) {
        return position;
    } // returns position in contact_list

    public View getView(int position, View convertView, ViewGroup parent) { // Get the view, our main function
        ViewHolder holder = null;
        if(convertView == null) { // What this does is recycle our list views, which means that new lsit
            holder = new ViewHolder(); // objects are not replaced, but rather reused and changed. Of course
                                    // This has its own share of problems...
                                    // Check out the Slow Loading Images in the documentation.txt
            convertView = inflater.inflate(resource , null);
            holder.NameTextView = (TextView) convertView.findViewById(R.id.firstLine); // add name line TextView to holder (firstLine)
            holder.MobileTextView = (TextView) convertView.findViewById(R.id.secondLine); // add mobile line TextView to holder (secondLine)
            holder.image = (ImageView) convertView.findViewById(R.id.profilePic); // add Image ImageView to holder (profilePic)
            convertView.setTag(holder);
            holder.favStar = (ImageView) convertView.findViewById(R.id.favoriteStar);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.NameTextView.setText(contact_list.get(position).getName()); // set Name
        //holder.MobileTextView.setText(contact_list.get(position).getPhone().getMobile()); // set Number
        holder.image.setImageResource(R.drawable.ic_launcher); // When an image is not loaded yet, set it to
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
}

