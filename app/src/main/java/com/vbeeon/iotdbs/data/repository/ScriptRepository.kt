package com.vbeeon.iotdbs.data.repository


import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.ScriptDao
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity


class ScriptRepository(val scriptDao: ScriptDao) {

    fun loadScriptByRoomId(room_id: Int) :  LiveData<List<ScriptEntity>>{
        return scriptDao.loadScriptByType(room_id)
    }
    fun loadAllScript() :  LiveData<List<ScriptEntity>>{
        return scriptDao.loadAllScript()
    }
    fun loadAllScriptbByType(type: Int) :  LiveData<List<ScriptEntity>>{
        return scriptDao.loadAllScript()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: ScriptEntity) {
        scriptDao.insertEntity(obj)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertList(list: List<ScriptEntity>) {
        scriptDao.insertListEntity(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: ScriptEntity) {
        scriptDao.updatetoDao(obj)
    }
}