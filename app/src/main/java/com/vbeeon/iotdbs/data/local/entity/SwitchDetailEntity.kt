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
@Entity(tableName = "switch_detail_entity",   foreignKeys = arrayOf(
        ForeignKey(entity = SwitchEntity::class,
                parentColumns = arrayOf("switch_id"),
                childColumns = arrayOf("switch_id"),
                onDelete = CASCADE)))
data class SwitchDetailEntity(
    @PrimaryKey()
    @ColumnInfo(name = "switch_detail_id")
    val id: Int ,
    @ColumnInfo(name = "switch_id")
    var idSwitch: Int ,
    @ColumnInfo(name = "switch_detail_name")
    val name: String,
    @ColumnInfo(name = "is_checked")
    var isChecked : Boolean= false

)