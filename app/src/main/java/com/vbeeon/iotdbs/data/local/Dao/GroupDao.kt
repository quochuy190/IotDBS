package com.vbeeon.iotdbs.data.local.Dao


import androidx.room.*
import com.vbeeon.iotdbs.data.local.entity.GroupEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity
import io.reactivex.rxjava3.core.Single

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGroupObj(group: GroupEntity)

    @Query("SELECT * FROM `group`")
    fun loadAllGroup(): List<GroupEntity>

    @Query("SELECT * FROM `group` WHERE rn IN (:id)")
    fun loadGroupById(id: Int): GroupEntity

    @Query("DELETE FROM `group` WHERE rn = :id")
    fun delete(id: Int)

    @Query("DELETE FROM `group`")
    fun deleteAll()

    @Update
    fun updatetoDao(group : GroupEntity?)
}