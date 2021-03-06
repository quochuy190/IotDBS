package com.vbeeon.iotdbs.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.switchDetail.SwitchDetailFragment
import com.vbeeon.iotdbs.presentation.fragment.switchDetail.SwitchDimmingDetailFragment
import com.vbeeon.iotdbs.utils.openFragment
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class SwitchDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val id = intent.getStringExtra(ConstantCommon.KEY_SEND_SWICH_ID)
        val name = intent.getStringExtra(ConstantCommon.KEY_SEND_SWICH_NAME)
        val floor = intent.getIntExtra(ConstantCommon.KEY_SEND_SWICH_FLOOR, -1)
        val TYPE = intent.getIntExtra(ConstantCommon.KEY_SEND_SWICH_TYPE, 0)
        if (TYPE == 5) {
            openFragment(SwitchDimmingDetailFragment.newInstance(id!!, name!!, floor), false)
        } else
            openFragment(SwitchDetailFragment.newInstance(id!!, name!!, floor), false)
    }
}