package com.vbeeon.iotdbs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vbeeon.iotdbs.data.local.Dao.*
import com.vbeeon.iotdbs.data.local.entity.*

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-January-2021
 * Time: 23:30
 * Version: 1.0
 */
@Database(entities = arrayOf(RoomEntity::class, UserEntity::class,
        SwitchEntity::class, SwitchDetailEntity::class, ScriptEntity::class, GroupEntity::class), version = 1)
@TypeConverters(StringListConverter::class)
abstract class IoTDbsDatabase : RoomDatabase() {

    abstract fun roomDao(): RoomDao
    abstract fun switchDao(): SwitchDao
    abstract fun switchDetailDao(): SwitchDetailDao
    abstract fun scriptDao(): ScriptDao
    abstract fun userDao(): UserDao

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