package com.vbeeon.iotdbs
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.gson.Gson
import timber.log.Timber

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:00
 * Version: 1.0
 */
class IotDBSApplication : Application() {
    companion object {
        lateinit var instance: IotDBSApplication
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    var gSon: Gson? = null


    override fun onCreate() {
        super.onCreate()
        gSon = Gson()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        //AppInitializeManager.init(this)
    }

}