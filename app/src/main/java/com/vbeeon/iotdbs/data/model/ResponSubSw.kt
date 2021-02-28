package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class ResponSubSw(
    @SerializedName("responseStatusCode")
    val responseStatusCode: Int,
    @SerializedName("originatingTimestamp")
    val originatingTimestamp :String,
    @SerializedName("content")
    var content: ResponSwitch
)