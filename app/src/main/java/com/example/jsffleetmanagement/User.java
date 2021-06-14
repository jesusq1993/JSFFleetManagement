package com.example.jsffleetmanagement;

public class User {

    String first_name;
    String last_name;
    String email;

    public User(){

    }

    public User(String fName, String lName, String email){
        this.first_name = fName;
        this.last_name = lName;
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }
}
