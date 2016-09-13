package com.example.stan.phonebook;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

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
*
*
*
*/


public class ContactFragment extends ListFragment {
    OnContactSelectedListener mCallback;

    List<ContactDetails> retrievedContactDetailsList; // Initialize a list of class ContactDetails
                                                    // Because the JSON used contains an array of
                                                    // contacts, we need to pull out a full list,
                                                    // not just a single contact.
    List<MoreContactDetails> MoreContactDetailsList; // Initialize a list of the class MoreContactDetails
                                                    // Which contains objects that have more date for each
                                                    // contact, pulled from their respective DetailsURL
                                                    // Check out MoreContactDetails.Class for more details


    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnContactSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void openContactInfo(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_contact_list, container, false); // Set our fragment_contact_list.xml as our view (list view)

        retrievedContactDetailsList = MainActivity.ContactDetailsList; // pull in the ContactDetails list from MainActivity's call of RetrieveContactDetails
        MoreContactDetailsList = MainActivity.MoreContactDetailsList; // Pull out this date from MainActivity. I hope this isn't making things hella slow
        // Question: Is this a smart thing to do? I'm not sure yet

        CustomAdapter adapter = new CustomAdapter(getActivity(), // Here we use our CustomAdapter to set up our list items.
                                                                // As you can see, fragment_contact_list_item.xml is being used
                                                                // as our list item, while retrievedContactDetailsList is the list
                                                                // of objects we are pulling values from.
                                                                // Check out CustomAdapter.Java for more info

                R.layout.fragment_contact_list_item, retrievedContactDetailsList, MoreContactDetailsList);


        setListAdapter(adapter); // set it as our list adapter
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

    @Override
    // What happens when an item is clicked
    public void onListItemClick(ListView l, View v, int position, long id) {

        mCallback.openContactInfo(position); // Run openContactInfo for the contact selected (passes its position)

        getListView().setSelector(android.R.color.holo_green_dark); // Colors! Just when selected.
    }

}