package com.vbeeon.iotdbs.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.MainFragment
import com.vbeeon.iotdbs.presentation.fragment.account.CreateAccFragment
import com.vbeeon.iotdbs.presentation.fragment.account.InitRoomInAccFragment
import com.vbeeon.iotdbs.presentation.fragment.login.LoginFragment
import com.vbeeon.iotdbs.presentation.fragment.script.SetupScriptFragment
import com.vbeeon.iotdbs.presentation.fragment.switchDetail.SwitchDetailFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splashscreen.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(InitRoomInAccFragment(), false)
    }
}