package com.vbeeon.iotdbs.data.local.Dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao
interface SwitchDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEntity(room: SwitchDetailEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListEntity(list: List<SwitchDetailEntity>)
    //ORDER BY room_name ASC
    @Query("SELECT * FROM switch_detail_entity")
    fun loadAllSubSwitch(): List<SwitchDetailEntity>

    @Query("SELECT * FROM switch_detail_entity WHERE switch_id IN (:switch_id)")
    fun loadSubSwitchByIdSwitch(switch_id: String): LiveData<List<SwitchDetailEntity>>

    @Query("SELECT * FROM switch_detail_entity WHERE switch_id IN (:switch_ids)")
    fun loadSubSwitchByIdList(switch_ids: List<String>): LiveData<List<SwitchDetailEntity>>

    @Query("DELETE FROM switch_detail_entity WHERE switch_id = :id")
    fun deleteObj(id: Int)

    @Query("DELETE FROM switch_detail_entity")
    fun deleteAll()

    @Update
    fun updatetoDao(user: SwitchDetailEntity?)
}