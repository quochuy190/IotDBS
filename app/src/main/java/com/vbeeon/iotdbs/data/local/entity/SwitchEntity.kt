package com.vbeeon.iotdbs.data.local.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:16
 * Version: 1.0
 */
@Entity(tableName = "switch_entity",   foreignKeys = arrayOf(
        ForeignKey(entity = RoomEntity::class,
                parentColumns = arrayOf("room_id"),
                childColumns = arrayOf("room_id"),
                onDelete = CASCADE)))
data class SwitchEntity(
    @PrimaryKey()
    @ColumnInfo(name = "switch_id")
    val id: Int ,
    @ColumnInfo(name = "room_id")
    var idRoom: Int ,
    @ColumnInfo(name = "switch_name")
    val name: String,
    @ColumnInfo(name = "is_checked")
    var isChecked : Boolean= false,
    @ColumnInfo(name = "switch_type")
    var type : Int //công tắc 1, công tắc 2, công tắc 3, ...

)