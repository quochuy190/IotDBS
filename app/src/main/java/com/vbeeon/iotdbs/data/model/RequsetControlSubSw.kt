package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class RequsetControlSubSw(
        @SerializedName("m2m:cin")
        val control: ControlSwObj) //0 off, 1// on