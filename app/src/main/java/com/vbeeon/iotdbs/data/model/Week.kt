package com.vbeeon.iotdbs.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import java.io.Serializable

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:16
 * Version: 1.0
 */

data class Week(
    val id: Int,
    var name: String,
    var isChecked: Boolean = false,
    var type: Int, //công tắc 1, công tắc 2, công tắc 3, ...,
    var description: String
) : Serializable