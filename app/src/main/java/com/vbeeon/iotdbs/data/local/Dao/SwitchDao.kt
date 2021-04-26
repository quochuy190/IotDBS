package com.vbeeon.iotdbs.data.local.Dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import io.reactivex.rxjava3.core.Single

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao
interface SwitchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRoomEntity(room: SwitchEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListEntity(list: List<SwitchEntity>)
    //ORDER BY room_name ASC
    @Query("SELECT * FROM switch_entity")
    fun loadAllSwitch(): LiveData<List<SwitchEntity>>

    @Query("SELECT * FROM switch_entity WHERE room_id IN (:room_id)")
    fun loadSwitchByIdRoom(room_id: Int): LiveData<List<SwitchEntity>>

    @Query("SELECT * FROM switch_entity WHERE floor IN (:floor)")
    fun loadSwitchByFloor(floor: Int): LiveData<List<SwitchEntity>>

    @Query("DELETE FROM switch_entity WHERE switch_id = :id")
    fun deleteObj(id: String)

    @Query("DELETE FROM switch_entity")
    fun deleteAll()

    @Update
    fun updatetoDao(user: SwitchEntity?)

    @Update
    fun updateListToDao(user: List<SwitchEntity>?)
}