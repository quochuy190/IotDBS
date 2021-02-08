package com.vbeeon.iotdbs.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.RoomDao
import com.vbeeon.iotdbs.data.local.Dao.SwitchDao
import com.vbeeon.iotdbs.data.local.Dao.SwitchDetailDao
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.User
import com.vbeeon.iotdbs.retrofit.RetrofitClient
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubSwichRepository(val roomDao: SwitchDetailDao) {

    fun loadSwitchByRoomId(room_id: String) :  LiveData<List<SwitchDetailEntity>>{
        return roomDao.loadSubSwitchByIdSwitch(room_id)
    }
    fun loadAllSubSwitch() : List<SwitchDetailEntity>{
        return roomDao.loadAllSubSwitch()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: SwitchDetailEntity) {
        roomDao.insertEntity(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertList(list: List<SwitchDetailEntity>) {
        roomDao.insertListEntity(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: SwitchDetailEntity) {
        roomDao.updatetoDao(obj)
    }
}