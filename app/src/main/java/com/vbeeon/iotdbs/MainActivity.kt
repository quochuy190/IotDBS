package com.vbeeon.iotdbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.MainFragment
import com.vbeeon.iotdbs.presentation.fragment.MainUserSubFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.openFragment
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()

    }

    private fun initEvent() {
        var jsonDevice = SharedPrefs.instance.get(ConstantCommon.KEY_SAVE_LOGIN_USER_LIST_DEVICE, String::class.java)
        if (jsonDevice!= null&&jsonDevice.length>0){
            openFragment(MainUserSubFragment(), false)
        }else
            openFragment(MainFragment(), false)
    }
}