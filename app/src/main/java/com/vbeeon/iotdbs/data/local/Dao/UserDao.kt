package com.vbeeon.iotdbs.data.local.Dao


import androidx.room.*
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
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserObj(user: UserEntity)

    @Query("SELECT * FROM user")
    fun loadAllUser(): List<UserEntity>

    @Query("SELECT * FROM user WHERE id IN (:id)")
    fun loadUserById(id: Int): UserEntity

    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Update
    fun updatetoDao(user: UserEntity?)
}