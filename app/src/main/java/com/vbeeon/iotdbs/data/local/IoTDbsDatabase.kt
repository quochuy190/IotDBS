package com.vbeeon.iotdbs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vbeeon.iotdbs.data.local.Dao.RoomDao
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-January-2021
 * Time: 23:30
 * Version: 1.0
 */
@Database(entities = arrayOf(RoomEntity::class, UserEntity::class), version = 1)
abstract class IoTDbsDatabase : RoomDatabase() {

    abstract fun roomDao(): RoomDao

    companion object {
        private var INSTANCE: IoTDbsDatabase? = null
        fun getInstance(context: Context): IoTDbsDatabase? {
            if (INSTANCE == null) {
                synchronized(IoTDbsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IoTDbsDatabase::class.java, "IoTDbsDatabase.db")
                            .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}