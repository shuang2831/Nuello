package com.example.stan.phonebook;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Stan on 9/15/2016.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//    private final TextView bakeryName;
//    private final TextView address;
//    private final TextView description;
//    private final TextView phone;
//    private final TextView website;

    private final TextView name;

    private UserInfo cd;
    private Context context;

    private NetworkImageView propic;
    private ImageLoader imageLoader;

    public ContactViewHolder(Context context, View itemView) {

        super(itemView);

        // 1. Set the context
        this.context = context;

        // 2. Set up the UI widgets of the holder
        this.name = (TextView) itemView.findViewById(R.id.firstLine);
        this.propic = (NetworkImageView) itemView.findViewById(R.id.profilePic);
//        this.bakeryName = (TextView) itemView.findViewById(R.id.bakery_name);
//        this.address = (TextView) itemView.findViewById(R.id.bakery_address);
//        this.description = (TextView) itemView.findViewById(R.id.bakery_description);
//        this.phone = (TextView) itemView.findViewById(R.id.bakery_phone);
//        this.website = (TextView) itemView.findViewById(R.id.bakery_website);

        // 3. Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);
    }

    public void bindContact(UserInfo cd) {

        // 4. Bind the data to the ViewHolder
        this.cd = cd;
//        this.bakeryName.setText(bakery.bakeryName);
//        this.address.setText(bakery.address);
//        this.phone.setText(bakery.phoneNumber);
//        this.website.setText(bakery.websiteUrl);
//        this.description.setText(bakery.description);
//        this.bakeryLogo.setImageBitmap(bakery.logo);
        this.name.setText(cd.getName());
        if (cd.getPropicLocation() == ""){
            this.propic.setImageResource(R.drawable.ic_launcher);
        }else {
            //this.propic.setImageResource(R.drawable.ic_launcher);
            imageLoader = LoadImage.getInstance(context.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(cd.getPropicLocation(), ImageLoader.getImageListener(propic,
                    R.drawable.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            this.propic.setImageUrl(cd.getPropicLocation(), imageLoader);
        }
    }

    @Override
    public void onClick(View v) {

        // 5. Handle the onClick event for the ViewHolder
        if (this.cd != null) {

            //Toast.makeText(this.context, "Clicked on " + this.cd.bakeryName, Toast.LENGTH_SHORT ).show();
            openContactInfo(getLayoutPosition());


        }
    }
    private void loadImage(){

        imageLoader = LoadImage.getInstance(context.getApplicationContext())
                .getImageLoader();
        imageLoader.get(cd.getPropicLocation(), ImageLoader.getImageListener(propic,
                R.drawable.ic_launcher, android.R.drawable
                        .ic_dialog_alert));
        propic.setImageUrl(cd.getPropicLocation(), imageLoader);
    }

    public void openContactInfo(int position) {
        // The user selected a contact form the list!
        //vpPager.setCurrentItem(4);
        // Create fragment and give it an argument for which contact was selected

        if (((FragmentActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            InfoFragment newFragment = new InfoFragment();
            Bundle args = new Bundle();
            args.putInt(InfoFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();

            // Replace current fragment in the fragment_container view (ContactFragment) with this fragment (InfoFragment),
            // and add the transaction to the back stack
            transaction.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction (bring up the new view)
            transaction.commit();
        }

    }
}

