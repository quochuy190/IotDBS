package com.vbeeon.iotdbs.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.login.IntroduceFragment
import com.vbeeon.iotdbs.presentation.fragment.login.LoginFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.openFragment
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isFirs =
            SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
        if (!isFirs)
            openFragment(IntroduceFragment(), false)
        else
            openFragment(LoginFragment(), false)
    }
}