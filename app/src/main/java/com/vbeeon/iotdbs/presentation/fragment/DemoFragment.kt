package com.vbeeon.iotdbs.presentation.fragment

import android.os.Bundle
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*


@Suppress("DEPRECATION")
class DemoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    companion object {
        fun newInstance(id: String): DemoFragment {
            val fragment = DemoFragment()
            val args = Bundle()
            args.putString("switch_id", id)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_timer
    }

    override fun initView() {

    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}