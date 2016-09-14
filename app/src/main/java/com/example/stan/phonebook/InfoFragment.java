
package com.example.stan.phonebook;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

public class InfoFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    List<UserInfo> if_RetrievedContactDetailsList; // Initialize a list of class ContactDetails
                                                        // Because the JSON used contains an array of
                                                        // contacts, we need to pull out a full list,
                                                        // not just a single contact.
                                                        // Just like the cone in ContactFragment
                                                        // "if_" stands for InfoFragment, but I know that can get confusing. I should change it...

    List<MoreContactDetails> if_MoreContactDetailsList; // Initialize a list of the class MoreContactDetails
                                                    // Which contains objects that have more date for each
                                                    // contact, pulled from their respective DetailsURL
                                                    // Check out MoreContactDetails.Class for more details

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if_RetrievedContactDetailsList = MainActivity.FriendDetailList; // Again like in ContactFragment, is this a good idea?
        //if_MoreContactDetailsList = MainActivity.MoreContactDetailsList; // Pull out this date from MainActivity. I hope this isn't making things hella slow

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION); // Save the position
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        return view; // return view
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

    public void updateContactDeats(int position) {

        // This big hunk of code should be straightforward. It sets the Textviews and images in the
        // Contact Details view. We pull our data from both if_RetrievedContactDetailsList and
        // MoreContactDetailsList in order to do so.
//
//        ImageView bigImage = (ImageView) getActivity().findViewById(R.id.bigProfilePic);
//        new LoadImage(bigImage).execute(if_MoreContactDetailsList.get(position).getLargeImageURL()); // Set big image
//                                                                                                // Check LoadImage.java for more details
//                                                                                                // Issue, it takes so long to load
        TextView employeeName = (TextView) getActivity().findViewById(R.id.nameLine); // Set name
        employeeName.setText(if_RetrievedContactDetailsList.get(position).getName());

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