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
@Entity(tableName = "room_entity")
data class RoomEntity(
    @PrimaryKey()
    @ColumnInfo(name = "room_id")
    val id: Int ,
    @ColumnInfo(name = "room_name")
    val name: String,
    @ColumnInfo(name = "floor")
    val floor: Int,
    @ColumnInfo(name = "is_selected")
    var isSelected : Boolean= false

)