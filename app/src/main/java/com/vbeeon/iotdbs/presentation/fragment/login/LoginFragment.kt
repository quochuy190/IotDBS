package com.vbeeon.iotdbs.presentation.fragment.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class LoginFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        buttonLogin.setOnSafeClickListener {
           // (context as LoginActivity).openFragment(ConfigWifiFragment(), true)
            SharedPrefs.instance.put(ConstantCommon.IS_FIRST_OPEN_APP, true)
            ( context as LoginActivity).launchActivity<MainActivity> {  }
            ( context as LoginActivity).finish()
        }
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}