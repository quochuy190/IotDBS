package com.vbeeon.iotdbs.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.model.User
import com.vbeeon.iotdbs.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RoomRepository {

    val resRoom = MutableLiveData<List<RoomEntity>>()

    fun loadData(context: Context, lifecycleOwner: LifecycleOwner ){
        IoTDbsDatabase.getInstance(context)!!.roomDao().loadAllRoom().observe(lifecycleOwner, Observer {
            resRoom.postValue(it)
        })
       // resRoom.postValue()
    }
}