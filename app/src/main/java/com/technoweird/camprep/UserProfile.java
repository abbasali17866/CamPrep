package com.technoweird.camprep;

public class UserProfile {
    public String name;
    public String email;
    public int Contact_no;

    public UserProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserProfile(String name, String email, int contact_no) {
        this.name = name;
        this.email = email;
        Contact_no = contact_no;
    }
}
