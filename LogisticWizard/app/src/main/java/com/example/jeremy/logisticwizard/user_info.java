package com.example.jeremy.logisticwizard;


public class user_info{
    public String user_id;
    public String password;
    public String Name;
    public String Phone;
    public String Role;



    public user_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user_info(String user_id, String password, String name, String phone, String role) {
        this.user_id = user_id;
        this.password = password;
        this.Name = name;
        this.Phone = phone;
        this.Role = role;
    }
}