package com.vbeeon.iotdbs.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vbeeon.iotdbs.data.model.User
import com.vbeeon.iotdbs.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    val serviceSetterGetter = MutableLiveData<User>()

    fun getServicesApiCall(): MutableLiveData<User> {

        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.name

                serviceSetterGetter.value = User(0,msg)
            }
        })

        return serviceSetterGetter
    }
}