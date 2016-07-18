
package com.nuvoex.diesel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("mobile_number")
    @Expose
    public String mobileNumber;

}
