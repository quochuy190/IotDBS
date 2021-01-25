package com.vbeeon.iotdbs.data.local.Dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import io.reactivex.rxjava3.core.Single

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoomEntity(user: RoomEntity)

    @Query("SELECT * FROM room_entity")
    fun loadAllRoom(): LiveData<List<RoomEntity>>

    @Query("SELECT * FROM room_entity WHERE id IN (:id)")
    fun loadRoomById(id: Int): RoomEntity

    @Query("DELETE FROM room_entity WHERE id = :id")
    fun deleteObj(id: Int)

    @Query("DELETE FROM room_entity")
    fun deleteAll()

    @Update
    fun updatetoDao(user: RoomEntity?)
}