package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class BuildingFragment : BaseFragment() {
    val mListRoom: MutableList<RoomEntity> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterRoom : RoomBuildAdapter


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
        }) }!!
        rcvRoomBuildingView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        rcvRoomBuildingView.apply { adapter = adapterRoom }
       // recyclerView.layoutManager = LinearLayoutManager(this)


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
        })
    }


}