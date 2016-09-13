package com.example.stan.phonebook;

import java.net.URL;

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
public class ContactDetails {

    public static class contactNumbers{

        private String work, home, mobile;

        public String getWork(){

            return work;

        }
        public String getHome(){

            return home;

        }
        public String getMobile(){

            return mobile;

        }

        public void setWork(String workNum){

            work = workNum;
        }
        public void setHome(String homeNum){

            home = homeNum;
        }
        public void setMobile(String mobileNum){

            mobile = mobileNum;
        }

    }

    private contactNumbers phone;
    private String name;
    private int employeeId;
    private String company;

    private String detailsURL;
    private String smallImageURL;
    private String birthdate;

    public contactNumbers getPhone(){

        return phone;
    }
    public String getName(){

        return name;
    }
    public int getEmployeeId(){

        return employeeId;
    }
    public String getCompany(){

        return company;
    }
    public String getDetailsURL(){

        return detailsURL;
    }
    public String getSmallImageURL(){

        return smallImageURL;
    }
    public String getBirthdate(){

        return birthdate;
    }

    public void setPhone(contactNumbers phoneNumbers){

        phone = phoneNumbers;

    }
    public void setName(String n){

        name = n;

    }
    public void setEmployeeID(int emID){

        employeeId = emID;

    }
    public void setCompany(String com){

        company = com;

    }
    public void setDetailsURL(String deatURL){

        detailsURL = deatURL;

    }
    public void setSmallImageURL(String smaImURL){

        smallImageURL = smaImURL;

    }
    public void setBirthdate(String birdat){

        birthdate = birdat;

    }

}
