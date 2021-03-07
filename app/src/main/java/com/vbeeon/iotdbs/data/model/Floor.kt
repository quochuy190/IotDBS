package com.vbeeon.iotdbs.data.model

import com.vbeeon.iotdbs.data.local.entity.RoomEntity

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 07-March-2021
 * Time: 15:51
 * Version: 1.0
 */

data class Floor(val id: Int, val name : String, val listRoom : List<RoomEntity>)