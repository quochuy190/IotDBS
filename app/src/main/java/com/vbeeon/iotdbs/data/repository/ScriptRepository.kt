package com.vbeeon.iotdbs.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.RoomDao
import com.vbeeon.iotdbs.data.local.Dao.ScriptDao
import com.vbeeon.iotdbs.data.local.Dao.SwitchDao
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.User
import com.vbeeon.iotdbs.retrofit.RetrofitClient
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScriptRepository(val scriptDao: ScriptDao) {

    fun loadScriptByRoomId(room_id: Int) :  LiveData<List<ScriptEntity>>{
        return scriptDao.loadScriptByIdRoom(room_id)
    }
    fun loadAllScript() :  LiveData<List<ScriptEntity>>{
        return scriptDao.loadAllScript()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: ScriptEntity) {
        scriptDao.insertRoomEntity(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: ScriptEntity) {
        scriptDao.updatetoDao(obj)
    }
}