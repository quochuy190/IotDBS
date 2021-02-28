package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class ResponSwitch(
    @SerializedName("m2m_cin")
    var m2m : SubSwitch
) //0 off, 1// on)