package com.example.stan.phonebook;

/**
 * Created by Stan on 9/14/2016.
 */
public class PendingFriends {

    private String name;
    private int acceptCode;

    // GETTERS
    public String getName(){

        return name;
    }
    public int getAcceptCode(){

        return acceptCode;
    }

    // SETTERS
    public void setName(String n){

        name = n;

    }
    public void setAcceptCode(int ac) {

        acceptCode = ac;
    }
}
