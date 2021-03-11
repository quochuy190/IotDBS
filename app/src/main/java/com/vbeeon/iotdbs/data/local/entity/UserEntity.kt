package com.vbeeon.iotdbs.data.local.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:16
 * Version: 1.0
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_name")
    val name: String,
    @ColumnInfo(name = "password")
    val pass: String,
    @ColumnInfo(name = "user_birthday")
    val birthDay : String,
    @ColumnInfo(name = "list_room")
    val listRoom : String,
    @ColumnInfo(name = "sex")
    val sex: Int,    //0: male, 1: female
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "type")
    val type: Int //0 admin, 1 user
)