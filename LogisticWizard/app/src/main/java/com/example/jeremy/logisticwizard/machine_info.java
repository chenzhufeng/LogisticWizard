package com.example.jeremy.logisticwizard;

public class machine_info {
    public String machine_name;
    public String machine_descrip;
    public String machine_price;
    public String machine_location;
    public String machine_type;
    public String machine_parts;
    public String maintain_plan;
    public String machine_quant;
//
//
//
    public machine_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public machine_info(String machine_name, String machine_descrip, String machine_price,
                        String machine_location, String machine_type, String machine_parts,
                        String maintain_plan, String machine_quant) {
        this.machine_name = machine_name;
        this.machine_descrip = machine_descrip;
        this.machine_price = machine_price;
        this.machine_location = machine_location;
        this.machine_type = machine_type;
        this.machine_parts = machine_parts;
        this.maintain_plan = maintain_plan;
        this.machine_quant = machine_quant;

    }

//    public  machine_info(String machine_name){
//        this.machine_name = machine_name;
//    }
}
