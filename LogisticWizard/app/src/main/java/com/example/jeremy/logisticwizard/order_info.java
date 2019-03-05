package com.example.jeremy.logisticwizard;

public class order_info {
    public String order_title;
    public String order_descrip;
    public String order_image;
    public String order_priority;
    public String maintain_plan;
    public String order_note;
    public String order_status;
    public String order_dates;
    public String order_cost;

    public order_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public order_info(String order_title, String order_descrip, String order_image, String order_priority,
                      String maintain_plan, String order_note, String order_status, String order_dates,String order_cost){
        this.order_title=order_title;
        this.order_descrip=order_descrip;
        this.order_image=order_image;
        this.order_priority=order_priority;
        this.maintain_plan=maintain_plan;
        this.order_note=order_note;
        this.order_status=order_status;
        this.order_dates=order_dates;
        this.order_cost=order_cost;
    }
}
