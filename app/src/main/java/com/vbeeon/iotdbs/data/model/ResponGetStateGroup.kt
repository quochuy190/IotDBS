package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class ResponGetStateGroup(
    @SerializedName("responsePrimitive")
    val responsePrimitive: List<ResponSubSw>)