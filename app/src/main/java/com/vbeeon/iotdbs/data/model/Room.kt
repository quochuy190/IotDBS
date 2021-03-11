package com.vbeeon.iotdbs.data.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:16
 * Version: 1.0
 */
data class Room(
    val id: Int ,
    val name: String,
    val floor: Int,
    var isSelected : Boolean= false,
    var listSW : List<SwitchEntity>

)