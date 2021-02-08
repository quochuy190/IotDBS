package com.vbeeon.iotdbs.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.presentation.adapter.MainViewPagerAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.fragment.bottonBar.BuildingFragment
import com.vbeeon.iotdbs.presentation.fragment.bottonBar.ScriptFragment
import com.vbeeon.iotdbs.presentation.fragment.switchDetail.SwitchDetailFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


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
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(SwitchDetailFragment(), "")
        adapter.addFragment(BuildingFragment(), "")
        adapter.addFragment(DemoFragment(), "")
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
        val roomList: MutableList<RoomEntity> = ArrayList()

        roomList.add(RoomEntity(0,"Phòng tổng hợp", 1,true))
        roomList.add(RoomEntity(1,"Phòng làm việc", 1, false))
        roomList.add(RoomEntity(2,"Phòng họp 1", 1, false))
        roomList.add(RoomEntity(3,"Phòng họp 3",1,  false))
        roomList.add(RoomEntity(4,"Phòng phó gián đốc 1",1,  false))
        roomList.add(RoomEntity(5,"Phòng phó gián đốc 2",1,  false))
        roomList.add(RoomEntity(6,"Phòng ăn",1,  false))
        // floor 2
        roomList.add(RoomEntity(7,"Phòng họp",2,  true))
        roomList.add(RoomEntity(8,"Phòng giám đốc",2,  false))
        roomList.add(RoomEntity(9,"Phòng làm việc",2,  false))
        roomList.add(RoomEntity(10,"Phòng ăn",2,  false))
        for (item in roomList){
            mainViewModel.insert(item)
        }
        val roomSwitch: MutableList<SwitchEntity> = ArrayList()
        roomSwitch.add(SwitchEntity("SW00568",0,"Đèn", true, 0))
        roomSwitch.add(SwitchEntity("SW00584",1,"Phòng làm việc T1 trên", true, 0))
        roomSwitch.add(SwitchEntity("SW00581",1,"Phòng làm việc T1 dưới", true, 0))
        roomSwitch.add(SwitchEntity("SW00571",2,"Phòng họp 1", true, 0))
        roomSwitch.add(SwitchEntity("SW00570",3,"Phòng họp 3", true, 0))
        roomSwitch.add(SwitchEntity("SW00574",4,"Phòng phó giám đốc", true, 0))
        roomSwitch.add(SwitchEntity("SW00585",5,"Phòng phó giám đốc", true, 0))
        roomSwitch.add(SwitchEntity("SW00582",6,"Phòng Pantry", true, 0))

        roomSwitch.add(SwitchEntity("SW00580",7,"Phòng Pantry", true, 0))
        roomSwitch.add(SwitchEntity("SW00580",8,"Phòng Pantry", true, 0))

        initViewPager();
    }

    override fun observable() {

    }


}