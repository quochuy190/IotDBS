package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.adapter.MainViewPagerAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_script.*


@Suppress("DEPRECATION")
class BuildMainFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_building_main
    }

    override fun initView() {
        initViewPager()
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }

    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(BuildingAllFloorFragment(), "Tòa nhà Si")
        adapter.addFragment(BuildingFragment.newInstance(1), "Tầng 1")
        adapter.addFragment(BuildingFragment.newInstance(2), "Tầng 2")
        vpBuildingMain.adapter = adapter
        vpBuildingMain.setOffscreenPageLimit(3)
        vpBuildingMain.setPageScrollEnabled(true)
        vpBuildingMain.currentItem = 0
        tabBuildingMain.setupWithViewPager(vpBuildingMain)

    }
}