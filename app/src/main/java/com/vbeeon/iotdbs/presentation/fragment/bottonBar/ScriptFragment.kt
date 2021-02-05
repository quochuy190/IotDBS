package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.adapter.MainViewPagerAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.script.ListSciptFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_script.*


@Suppress("DEPRECATION")
class ScriptFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_script
    }

    override fun initView() {

    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
//            ConvertDigitalViewModel::class.java)
        initViewPager()
    }

    override fun observable() {

    }

    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ListSciptFragment(), "")
        adapter.addFragment(DemoFragment(), "")
        vpgScript.adapter = adapter
        vpgScript.setOffscreenPageLimit(2)
        vpgScript.setPageScrollEnabled(true)
        tabScript.setupWithViewPager(vpgScript)
        setupTabIcons()
    }
    private fun setupTabIcons() {
        tabScript.getTabAt(0)!!.setIcon(R.drawable.ic_eye)
        tabScript.getTabAt(1)!!.setIcon(R.drawable.ic_sun)
    }

}