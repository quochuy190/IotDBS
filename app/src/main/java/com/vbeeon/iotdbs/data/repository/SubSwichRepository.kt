package com.vbeeon.iotdbs.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.vbeeon.iotdbs.data.local.Dao.SwitchDetailDao
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import io.reactivex.rxjava3.core.Single
import timber.log.Timber

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
}