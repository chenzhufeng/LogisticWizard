package com.example.jeremy.logisticwizard.Tool;

public class tool_info {
    public String tool_name;
    public String tool_descrip;
    public String tool_price;
    public String tool_location;
    public String tool_type;
    //public String tool_parts;
    //public String maintain_plan;
    public String tool_quant;
    //
//
//
    public tool_info() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public tool_info(String tool_name, String tool_descrip, String tool_price,
                        String tool_location, String tool_type, String tool_quant) {
        this.tool_name = tool_name;
        this.tool_descrip = tool_descrip;
        this.tool_price = tool_price;
        this.tool_location = tool_location;
        this.tool_type = tool_type;
        this.tool_quant = tool_quant;

    }
}
