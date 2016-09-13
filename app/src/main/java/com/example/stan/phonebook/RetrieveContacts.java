package com.example.stan.phonebook;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Stan on 1/30/2016.
 *
 * So this is our Asynchrounous task for retrieving the information in the main JSON page and reading
 * it into a list of ContactDetails classes using the Jackson Library.
 */
public class RetrieveContacts extends AsyncTask<String, Void, List<ContactDetails>> {

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
