package com.vbeeon.iotdbs.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginSuperVisorRequest {
    @SerializedName("uuid")
    String uuid;
    @SerializedName("password")
    String password;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
