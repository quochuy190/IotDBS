package com.vbeeon.iotdbs.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginSupervisorRemoteEntity {
    @SerializedName("id")
    public Integer id;
    @SerializedName("fcm_token")
    public String fcm_token;
    @SerializedName("uuid")
    public String uuid;
    @SerializedName("role")
    public Integer role;
    @SerializedName("app_version")
    public String app_version;
    @SerializedName("os_version")
    public Long os_version;
    @SerializedName("device_mode")
    public String device_mode;
    @SerializedName("last_login")
    public String last_login;
    @SerializedName("last_logout")
    public String last_logout;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("token")
    public String token;
    @SerializedName("expires_in")
    public String expires_in;

}
