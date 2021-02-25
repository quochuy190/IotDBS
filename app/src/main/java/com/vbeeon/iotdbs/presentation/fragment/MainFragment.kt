package com.vbeeon.iotdbs.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.adapter.MainViewPagerAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.fragment.bottonBar.BuildMainFragment
import com.vbeeon.iotdbs.presentation.fragment.bottonBar.MenuFragment
import com.vbeeon.iotdbs.presentation.fragment.bottonBar.ScriptFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


@Suppress("DEPRECATION")
class MainFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initView() {
        pull_refesh.setOnRefreshListener {
           // refreshAction()                    // refresh your list contents somehow
            pull_refesh.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }
    }

    private fun initViewPager() {
        mainViewModel.exeGetStateFromRemote()
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ScriptFragment(), "")
        adapter.addFragment(BuildMainFragment(), "")
        adapter.addFragment(MenuFragment(), "")
        vp_main.adapter = adapter
        vp_main.setOffscreenPageLimit(3)
        vp_main.setPageScrollEnabled(false)
        vp_main.currentItem = 1
        bnv.setSelectedItemId(R.id.it_building);

        val menuItem = bnv.menu.findItem(R.id.it_building)
        menuItem.setIcon(R.drawable.ic_building)
        bnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.it_script -> {
                    vp_main.currentItem =0
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_home))
                }
                R.id.it_building -> {
                    vp_main.currentItem =1
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_monitoring))
                }
                R.id.it_menu -> {
                    vp_main.currentItem =2
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_online))
                }
            }
            true
        }
    }

//    private fun initViewPager() {
//        val adapter = MainViewPagerAdapter(childFragmentManager)
//        adapter.addFragment(SwitchDetailFragment(), "")
//        adapter.addFragment(BuildingFragment(), "")
//        adapter.addFragment(ScriptFragment(), "")
//
//        vp_main.adapter = adapter
//        vp_main.setOffscreenPageLimit(3)
//        vp_main.setPageScrollEnabled(false)
//        vp_main.currentItem = 1
//        bnv.setSelectedItemId(R.id.it_building);
//
//    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable -> showDialogMessage(context,
                getString(R.string.system_error)) })
        initViewPager();
    }

    override fun observable() {

    }


}