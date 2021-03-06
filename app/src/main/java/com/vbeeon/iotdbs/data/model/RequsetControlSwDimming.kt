package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class RequsetControlSwDimming(
        @SerializedName("m2m:cin")
        val control: ControlSwObjDimming) //0 off, 1// on