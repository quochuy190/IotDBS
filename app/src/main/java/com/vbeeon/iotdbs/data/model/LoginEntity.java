package com.vbeeon.iotdbs.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginEntity {
    @SerializedName("id")
    Long id;
    @SerializedName("username")
    String username;
    @SerializedName("deviceId")
    Long deviceId;
    @SerializedName("fullname")
    String fullName;
    @SerializedName("avatar")
    String avatar;
    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("token")
    String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
