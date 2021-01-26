package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchBuildingAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class BuildingFragment : BaseFragment() {
    val mListRoom: MutableList<RoomEntity> = ArrayList()
    val mListSwitch: MutableList<SwitchEntity> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterRoom : RoomBuildAdapter
    lateinit var adapterSwitch : SwitchBuildingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_building
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Tòa nhà"
        imgBack.visibility = View.INVISIBLE
        adapterRoom = context?.let { RoomBuildAdapter(it, doneClick = {
            for (item in mListRoom){
                if (mListRoom[it].id==item.id){
                    item.isSelected = true
                }else{
                    item.isSelected = false
                }
            }
            rcvRoomBuildingView.scrollToPosition(it)
            adapterRoom.notifyDataSetChanged()
            mainViewModel.loadDataSwitch(this, mListRoom[it].id)
        }) }!!
        rcvRoomBuildingView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        rcvRoomBuildingView.apply { adapter = adapterRoom }
       // recyclerView.layoutManager = LinearLayoutManager(this)
        initRcvSwitch()

    }

    private fun initRcvSwitch() {
        adapterSwitch = activity?.let {
            SwitchBuildingAdapter(it, doneClick = {

            })
        }!!
        rcvSwitchBuildingView.layoutManager = GridLayoutManager(context, 2)
        rcvSwitchBuildingView.apply { adapter = adapterSwitch }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val roomList: MutableList<RoomEntity> = ArrayList()
        roomList.add(RoomEntity(0,"Phòng khách tầng 1", true))
        roomList.add(RoomEntity(1,"Phòng bếp tâng 1", false))
        roomList.add(RoomEntity(2,"Phòng ngủ tầng 2", false))
        roomList.add(RoomEntity(3,"Phòng ngủ lớn tâng 2", false))
        for (item in roomList){
            mainViewModel.insert(item)
        }
        mainViewModel.loadData(this)

    }



    override fun observable() {
        mainViewModel.devicesRes.observe(this, Observer {
            mListRoom.clear()
            mListRoom.addAll(it)
            adapterRoom.setDatas(mListRoom)
            //create switch
            val roomSwitch: MutableList<SwitchEntity> = ArrayList()
            roomSwitch.add(SwitchEntity(0,0,"Công tắc 1 nút", true, 0))
            roomSwitch.add(SwitchEntity(1,0,"Công tắc 3 nút", false, 0))
            roomSwitch.add(SwitchEntity(2,0,"Công tắc rèm", false, 1))
            roomSwitch.add(SwitchEntity(3,1,"Công tắc 1 nút", true, 0))
            roomSwitch.add(SwitchEntity(4,1,"Cảm biến hông ngoại", false, 2))
            roomSwitch.add(SwitchEntity(6,2,"Công tắc rèm", false, 1))
            roomSwitch.add(SwitchEntity(5,2,"Công tắc rèm", false, 1))
            roomSwitch.add(SwitchEntity(7,2,"Công tắc 2 nút", false, 1))
            for (item in roomSwitch){
                mainViewModel.insertSwitch(item)
            }
            mainViewModel.loadDataSwitch(this, mListRoom[0].id)
        })
        mainViewModel.switchRespon.observe(this, Observer {
            mListSwitch.clear()
            mListSwitch.addAll(it)
            adapterSwitch.setDatas(mListSwitch)
        })
    }


}