package com.example.stan.phonebook;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Stan on 1/30/2016.
 *
 * This class is to be used by the Jackson reader
 * The reader will pull out the values from a JSON file and use the setter methods
 * to create an instance of this class with all the variables defined
 *
 *
 * The contents of this class are the values given by https://solstice.applauncher.com/external/contacts.json
 *
 * They include:
 *
 * Name
 * Employee Id
 * Company
 * DetailsURL (To be read into MoreContactDetails
 * SmallImageURL (Used for List thumbnail)
 * Birthdate (Given in Unix date time; must be converted to readable format)
 * Phone Numbers (work, home, and mobile)
 *
 */
public class UserInfo implements Comparable<UserInfo>{

    private int uid;
    private String name;
    private String username;
    private String status;
    private String topic;
    private String mood;
    private long lastUpdate;
    private String propicLocation;

    //private boolean visible = true;

    // GETTERS
    public int getUid(){

        return uid;
    }
    public String getName(){

        return name;
    }
    public String getUsername(){

        return username;
    }
    public String getStatus(){

        return status;
    }
    public String getTopic(){

        return topic;
    }
    public String getMood(){

        return mood;
    }
    public long getLastUpdate(){
        return lastUpdate;
    }

    public String getPropicLocation(){

        return propicLocation;
    }
//    public boolean getVisible(){
//
//        return visible;
//    }


    // SETTERS
    public void setUid(int enteredID) {

        uid = enteredID;
    }
    public void setName(String n){

        name = n;

    }
    public void setUsername(String un){

        username = un;
    }
//    public void setVisible(boolean isVisible){
//
//        visible = isVisible;
//
//    }
    public void setStatus(String sta){

        status = sta;

    }
    public void setTopic(String top){

        topic = top;

    }
    public void setMood(String md){

        mood = md;

    }
    public void setLastUpdate(String lu){

        lastUpdate = getUnix(lu);

    }

    public void setPropicLocation(String ppl){

        propicLocation = ppl;

    }

    public long getUnix(String date){

        // This function is used for the birthday.
        // When we pull out the birthdate from the JSON URL, its given to use in a string
        // representing the Unix Stamp Time Format. This is a way to convert that into
        // a readable birthdat String.
        Date d = null;

        //Date date = new Date(l*1000L); // convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Format: ex December 21, 1994 (Guess who just turned 21?)
        try {
            d = sdf.parse(date);
            //sdf.setTimeZone(TimeZone.getTimeZone("CST")); // Central TimeZone
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = d.getTime();
        return l;

    }

    public static class OrderByAmount implements Comparator<UserInfo> {

        @Override
        public int compare(UserInfo o1, UserInfo o2) {
            return o1.lastUpdate < o2.lastUpdate ? 1 : (o1.lastUpdate > o2.lastUpdate ? -1 : 0);
        }
    }
    @Override
    public int compareTo(UserInfo o) {
        return this.lastUpdate < o.lastUpdate ? 1 : (this.lastUpdate > o.lastUpdate ? -1 : 0);
    }

}
