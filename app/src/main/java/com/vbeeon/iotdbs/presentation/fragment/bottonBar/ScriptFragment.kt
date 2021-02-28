package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.adapter.MainViewPagerAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.script.AutoScriptFragment
import com.vbeeon.iotdbs.presentation.fragment.script.ListSciptFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_script.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


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
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Kịch bản"
        imgBack.visibility = View.INVISIBLE
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
        adapter.addFragment(ListSciptFragment(), "Ngữ cảnh")
        adapter.addFragment(AutoScriptFragment(), "Tự động")
        vpgScript.adapter = adapter
        vpgScript.setOffscreenPageLimit(2)
        vpgScript.setPageScrollEnabled(false)
        tabScript.setupWithViewPager(vpgScript)
        setupTabIcons()
    }
    private fun setupTabIcons() {
        tabScript.getTabAt(0)!!.setIcon(R.drawable.tab_script_one)
        tabScript.getTabAt(1)!!.setIcon(R.drawable.tab_script_two)

        tabScript.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(Color.parseColor("#cccccc"), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}