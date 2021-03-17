package com.vbeeon.iotdbs.data.local.Dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
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
interface ScriptDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEntity(room: ScriptEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListEntity(list: List<ScriptEntity>)
    //ORDER BY room_name ASC
    @Query("SELECT * FROM script_entity")
    fun loadAllScript(): LiveData<List<ScriptEntity>>

    @Query("SELECT * FROM script_entity WHERE type IN (:type)")
    fun loadScriptByType(type: Int): LiveData<List<ScriptEntity>>

    @Query("DELETE FROM script_entity WHERE script_id = :id")
    fun deleteObj(id: Long)

    @Query("DELETE FROM script_entity")
    fun deleteAll()

    @Update
    fun updatetoDao(user: ScriptEntity?)
}