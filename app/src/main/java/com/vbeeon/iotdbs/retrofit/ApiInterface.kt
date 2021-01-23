package com.vbeeon.iotdbs.retrofit

import com.vbeeon.iotdbs.data.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("services")
    fun getServices() : Call<User>

}