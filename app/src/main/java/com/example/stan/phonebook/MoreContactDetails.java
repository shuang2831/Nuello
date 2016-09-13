package com.example.stan.phonebook;

/**
 * Created by Stan on 1/30/2016.
 *
 * Just like the ContactDetails class, this class is used to hold information read from JSON pages using Jackson.
 * It does so through the use of the various getters and setters.
 * The difference being that this class holds info from a contact's information specified in
 * his or her detailsURL. This includes:
 *
 * EmployeeId
 * Status as a Favorite
 * Large Image URL
 * Email
 * Website
 * Address (Street, City, State, Zip, Country, Latitude, Longitude)
 *
 */
public class MoreContactDetails {

    private int employeeId;
    private boolean favorite;
    private String largeImageURL;
    private String email;
    private String website;
    private addressDetails address;

    public class addressDetails{

        private String street, city, state, country, zip;
        private int latitude, longitude;

        public String getStreet(){

            return street;
        }
        public String getCity(){

            return city;
        }
        public String getState(){
            return state;
        }
        public String getCountry(){
            return country;
        }
        public String getZip(){
            return zip;
        }
        public int getLatitude(){
            return latitude;
        }
        public int getLongitude(){
            return longitude;
        }


        public void setStreet(String street) {
            this.street = street;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }
    }

    public addressDetails getAddress() {
        return address;
    }

    public void setAddress(addressDetails address) {
        this.address = address;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
