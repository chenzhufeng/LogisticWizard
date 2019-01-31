package com.example.jeremy.logisticwizard;


public class user_info{
    public String user_id;
    public String password;
//    public String name;
//    public String position;
//    public String phone;
//    public String email;
//    public String mail_address;
//    public String work_time;


    public user_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user_info(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }
}