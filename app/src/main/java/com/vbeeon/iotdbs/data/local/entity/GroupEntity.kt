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
@Entity(tableName = "group")
data class GroupEntity(
        @PrimaryKey()
        @ColumnInfo(name = "rn")
        val rn: String,
        @ColumnInfo(name = "mnm")
        val mnm: Int,
        @ColumnInfo(name = "mt")
        val mt: Int,
        @ColumnInfo(name = "mid")
        var mid: List<String>
)