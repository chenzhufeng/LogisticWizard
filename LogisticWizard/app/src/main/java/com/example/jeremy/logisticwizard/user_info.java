package com.example.jeremy.logisticwizard;


public class user_info{
    public String user_id;
    public String password;
    public String name;
    public String phone;
    public String Address;
    public String worktime;


    public user_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user_info(String user_id, String password, String name, String phone, String Address, String worktime) {
        this.user_id = user_id;
        this.password = password;
    }
}