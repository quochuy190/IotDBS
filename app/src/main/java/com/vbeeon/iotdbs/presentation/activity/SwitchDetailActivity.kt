package com.vbeeon.iotdbs.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.MainFragment
import com.vbeeon.iotdbs.presentation.fragment.login.LoginFragment
import com.vbeeon.iotdbs.presentation.fragment.switchDetail.SwitchDetailFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splashscreen.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class SwitchDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val id = intent.getStringExtra(ConstantCommon.KEY_SEND_SWICH_ID)
        val name = intent.getStringExtra(ConstantCommon.KEY_SEND_SWICH_NAME)
        openFragment(SwitchDetailFragment.newInstance(id!!, name!!), false)
    }
}