package com.vbeeon.iotdbs.data.local.entity

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
@Entity(tableName = "script_entity")
data class ScriptEntity(
        @PrimaryKey()
        @ColumnInfo(name = "script_id")
        val id: Long,
        @ColumnInfo(name = "script_des")
        var description: String,
        @ColumnInfo(name = "script_name")
        val name: String,
        @ColumnInfo(name = "is_checked")
        var isChecked: Boolean = false,
        @ColumnInfo(name = "mid")
        var mid: List<String>,
        @ColumnInfo(name = "control")
        var control :Int,
        @ColumnInfo(name = "type")
        var type :Int

)