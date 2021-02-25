package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

data class CreateGroupRequest(
        @SerializedName("m2m:grp")
        val m2m: Group)