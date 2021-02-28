package com.vbeeon.iotdbs.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.vbeeon.iotdbs.data.local.Dao.SwitchDetailDao
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.util.concurrent.Callable

class SubSwichRepository(val subSwDao: SwitchDetailDao) {

    fun loadSwitchBySwitchId(id: String) :  LiveData<List<SwitchDetailEntity>>{
        return subSwDao.loadSubSwitchByIdSwitch(id)
    }
    fun loadSwitchBySwitchIdList(ids: List<String>) : LiveData<List<SwitchDetailEntity>>{
        return subSwDao.loadSubSwitchByIdList(ids)
    }
    fun loadAllSubSwitch() : LiveData<List<SwitchDetailEntity>>{
        return subSwDao.loadAllSubSwitch()
    }

    fun loadAllSubSwitchFloor(floor: Int) : Single<List<SwitchDetailEntity>>{
        return Single.fromCallable(Callable {
            subSwDao.loadAllSubSwitchByFloor(floor)
        })
    }

    fun loadSubSwitchWithSwId(id: String) : Single<List<SwitchDetailEntity>>{
        return Single.fromCallable(Callable {
            subSwDao.loadAllSubSwitchBySWid(id)
        })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: SwitchDetailEntity) {
        subSwDao.insertEntity(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertList(list: List<SwitchDetailEntity>) {
        subSwDao.insertListEntity(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: SwitchDetailEntity) {
        subSwDao.updatetoDao(obj)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateList(list: List<SwitchDetailEntity>) {
        subSwDao.updateListtoDao(list)
    }
}