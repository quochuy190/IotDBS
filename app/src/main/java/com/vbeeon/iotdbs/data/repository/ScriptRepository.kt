package com.vbeeon.iotdbs.data.repository


import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.ScriptDao
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity


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