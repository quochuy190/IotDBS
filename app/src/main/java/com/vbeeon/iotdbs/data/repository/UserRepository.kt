package com.vbeeon.iotdbs.data.repository


import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.vbeeon.iotdbs.data.local.Dao.ScriptDao
import com.vbeeon.iotdbs.data.local.Dao.UserDao
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity

class UserRepository(val usertDao: UserDao) {

    fun loadScriptByRoomId(room_id: Int) :  LiveData<UserEntity>{
        return usertDao.loadUserById(room_id)
    }
    fun loadUserAndPass(user: String, pass: String) :  LiveData<List<UserEntity>>{
        return usertDao.getUserAndPass(user, pass)
    }

    fun loadAllUserByType(type :Int) :  LiveData<List<UserEntity>>{
        return usertDao.getUserbyType(type)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: UserEntity) {
        usertDao.insertUserObj(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: UserEntity) {
        usertDao.updatetoDao(obj)
    }
}