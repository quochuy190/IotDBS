package com.vbeeon.iotdbs.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.RoomDao
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.model.User
import com.vbeeon.iotdbs.retrofit.RetrofitClient
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomRepository(val roomDao: RoomDao) {

    val resRoom = MutableLiveData<List<RoomEntity>>()

    val allRooms: LiveData<List<RoomEntity>> = roomDao.loadAllRoom()

    fun loadRoomByFloor(floor: Int) : LiveData<List<RoomEntity>>{
        return roomDao.loadRoomByFloor(floor)
    }
    //val allRoomByFloor: LiveData<List<RoomEntity>> = roomDao.loadRoomByFloor()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: RoomEntity) {
        roomDao.insertRoomEntity(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertList(word: List<RoomEntity>) {
        roomDao.insertListEntity(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(word: RoomEntity) {
        roomDao.updatetoDao(word)
    }
}