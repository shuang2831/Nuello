package com.example.stan.phonebook;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Stan on 9/13/2016.
 */
public class RetrieveMoods {


    protected List<ContactDetails> doInBackground(String... urls) {

        URL url; // For now we're hardcoding in the web address
        List<ContactDetails> list; // initialize a list of ContactDetails

        try {
            String contactsURL = "https://solstice.applauncher.com/external/contacts.json"; // hardcoded web address
            url = new URL(contactsURL);
            ObjectMapper mapper = new ObjectMapper();
            try {
                list = mapper.readValue(url, new TypeReference<List<ContactDetails>>() { // use the mapper to read values
                });
                return list; // return when finished
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null; // if there are issues, return null
    }


}
