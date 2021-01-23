package com.vbeeon.iotdbs.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel


@Suppress("DEPRECATION")
class DemoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_demo
    }

    override fun initView() {
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}