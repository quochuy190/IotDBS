package com.vbeeon.iotdbs.presentation.fragment.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_config_gateway_one.*
import kotlinx.android.synthetic.main.fragment_config_gateway_two.*


@Suppress("DEPRECATION")
class ConfigWifiDetailFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_config_gateway_two
    }

    override fun initView() {
        buttonConfigWifiDetail.setOnSafeClickListener {
            (context as LoginActivity).launchActivity<MainActivity>()
        }
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}