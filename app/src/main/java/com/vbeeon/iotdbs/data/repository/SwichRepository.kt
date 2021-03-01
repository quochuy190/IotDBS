package com.vbeeon.iotdbs.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.RoomDao
import com.vbeeon.iotdbs.data.local.Dao.SwitchDao
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity


class SwichRepository(val roomDao: SwitchDao) {

    fun loadSwitchByRoomId(room_id: Int) :  LiveData<List<SwitchEntity>>{
        return roomDao.loadSwitchByIdRoom(room_id)
    }

    fun loadAllSwitch() :  LiveData<List<SwitchEntity>>{
        return roomDao.loadAllSwitch()
    }
    fun loadAllSwitchByFloor(floor: Int) :  LiveData<List<SwitchEntity>>{
        return roomDao.loadSwitchByFloor(floor)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: SwitchEntity) {
        roomDao.insertRoomEntity(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertList(obj: List<SwitchEntity>) {
        roomDao.insertListEntity(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: SwitchEntity) {
        roomDao.updatetoDao(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateList(obj: List<SwitchEntity>) {
        roomDao.updateListToDao(obj)
    }
}