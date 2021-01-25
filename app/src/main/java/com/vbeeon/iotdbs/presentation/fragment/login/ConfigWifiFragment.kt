package com.vbeeon.iotdbs.presentation.fragment.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_config_gateway_one.*
import kotlinx.android.synthetic.main.fragment_config_gateway_two.*
import kotlinx.android.synthetic.main.fragment_login.*


@Suppress("DEPRECATION")
class ConfigWifiFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_config_gateway_one
    }

    override fun initView() {
        buttonNextConfigWifi.setOnSafeClickListener {
            (context as LoginActivity).openFragment(ConfigWifiDetailFragment(), true)
        }
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}