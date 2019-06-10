package com.example.jeremy.logisticwizard;


public class user_info{
    public String Email;
    public String Name;
    public String Phone;
    public String Role;
    public String u_id;



    public user_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user_info(String Email, String name, String phone, String role, String u_id) {
        this.Email = Email;
        this.Name = name;
        this.Phone = phone;
        this.Role = role;
        this.u_id = u_id;
    }
}