package com.example.stan.phonebook;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Stan on 1/30/2016.
 *
 * Similar to the RetrieveContacts task, this task will retrive the info contained in a url. The difference
 * is that this one will only return on instance of the MoreContactDetails class while RetrieveContacts
 * returned an array of classes. Also this one does not use a hardcoded web address, but rather allows
 * the user to input parameters
 *
 */
public class RetrieveContactsMoreDetails extends AsyncTask<String, Void, MoreContactDetails> {

    protected MoreContactDetails doInBackground(String... urls) {

        URL url; // Initialize url object
        MoreContactDetails moreDetailsObject; // initialize instance of class

        try {

            url = new URL(urls[0]); // Take in the input parameter
            ObjectMapper mapper = new ObjectMapper();
            try {
                moreDetailsObject = mapper.readValue(url, new TypeReference<MoreContactDetails>() { /// read the data into the object
                });
                return moreDetailsObject; // return the object
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }


}