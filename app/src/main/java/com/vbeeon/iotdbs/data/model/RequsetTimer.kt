package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class RequsetTimer(
        @SerializedName("m2m:sch")
        val control: ControlSwObj) //0 off, 1// on