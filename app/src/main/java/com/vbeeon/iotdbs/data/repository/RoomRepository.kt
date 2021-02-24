package com.vbeeon.iotdbs.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vbeeon.iotdbs.data.local.Dao.RoomDao
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.model.*
import com.vbeeon.iotdbs.data.remote.ApiClient
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.util.concurrent.Callable

class RoomRepository(val roomDao: RoomDao) {
    val retrofit = ApiClient.getClient()

    val resRoom = MutableLiveData<List<RoomEntity>>()

    val allRooms: LiveData<List<RoomEntity>> = roomDao.loadAllRoom()

    fun loadRoomByFloor(floor: Int) : LiveData<List<RoomEntity>>{
        return roomDao.loadRoomByFloor(floor)
    }
    fun loadDatatoRemote(): Single<LiveData<List<RoomEntity>>>{
//
        return Single.fromCallable(Callable { roomDao.loadAllRoomAL() })
    }

    fun loginRemote(): Single<LoginSupervisorRemoteEntity>{
        val request = LoginSuperVisorRequest();
        Timber.e("call loginRemote")
        request.password = "fd6aa3889d2415bcbc13803f303f1137"
        request.uuid = "e92947d6-2dcf-3375-b3c8-7789f969de6a"
        return retrofit.loginSupervisor(request).map({ response ->
            if (response.errorCode == 0) {
                Timber.e("call api success")
                return@map response.data
            } else {
                throw ApiException(response.errorCode, response.errorMessage)
            }
        })
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
    private fun getTestApi(): Single<User> {

        return retrofit.getMovieDetails(1)
    }
}