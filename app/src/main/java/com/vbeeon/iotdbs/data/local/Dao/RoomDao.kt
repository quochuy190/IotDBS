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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRoomEntity(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListEntity(list: List<RoomEntity>)
    //ORDER BY room_name ASC
    @Query("SELECT * FROM room_entity")
    fun loadAllRoom(): LiveData<List<RoomEntity>>

    @Query("SELECT * FROM room_entity")
    fun loadAllRoomAL(): LiveData<List<RoomEntity>>

    @Query("SELECT * FROM room_entity WHERE floor IN (:id)")
    fun loadRoomByFloor(id: Int): LiveData<List<RoomEntity>>

    @Query("SELECT * FROM room_entity WHERE room_id IN (:id)")
    fun loadRoomById(id: Int): RoomEntity

    @Query("DELETE FROM room_entity WHERE room_id = :id")
    fun deleteObj(id: Int)

    @Query("DELETE FROM room_entity")
    fun deleteAll()

    @Update
    fun updatetoDao(user: RoomEntity?)

    @Update
    fun updateListtoDao(list: List<RoomEntity>?)
}