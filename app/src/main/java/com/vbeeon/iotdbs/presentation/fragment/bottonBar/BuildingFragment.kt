package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.presentation.activity.SwitchDetailActivity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchBuildingAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class BuildingFragment : BaseFragment() {
    val mListRoom: MutableList<RoomEntity> = ArrayList()
    val mListSwitch: MutableList<SwitchEntity> = ArrayList()
    val mListSW: MutableList<SwitchDetailEntity> = ArrayList()
    val mList: MutableList<Switch> = ArrayList()
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
            Timber.e(""+ mListRoom[it].id)
            mListSwitch.clear()
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
//                (context as MainActivity).
//                openFragment(SwitchDetailFragment.newInstance(mListSwitch[it].id,mListSwitch[it].name ), true)
                (context as MainActivity).launchActivity<SwitchDetailActivity>{
                    putExtra(ConstantCommon.KEY_SEND_SWICH_ID, mListSwitch[it].id)
                    putExtra(ConstantCommon.KEY_SEND_SWICH_NAME, mListSwitch[it].name)
                }
            })
        }!!
        rcvSwitchBuildingView.layoutManager = GridLayoutManager(context, 2)
        rcvSwitchBuildingView.apply { adapter = adapterSwitch }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.loadData(this)

    }



    override fun observable() {
        mainViewModel.devicesRes.observe(this, Observer {
            mListRoom.clear()
            mListRoom.addAll(it)
            adapterRoom.setDatas(mListRoom)
            //create switch
            Timber.e("room id"+ mListRoom[0].id)
            mListSwitch.clear()
            mainViewModel.loadDataSwitch(this, mListRoom[0].id)
        })
        mainViewModel.swRespon.observe(this, Observer {
            mListSwitch.clear()
            mListSwitch.addAll(it)
            Timber.e("switchs = "+it.size)
            var ids : MutableList<String> = mutableListOf()
            for (switch in it){
                ids.add(switch.id)
            }
            mainViewModel.loadSubSwitchBySwitchId(this, ids)
        })
        mainViewModel.switchRespon.observe(this, Observer {
            mList.clear()
            mListSW.clear()
            mListSW.addAll(it)
            Timber.e("size sw="+it.size)
            for (switch in mListSwitch){
                var subSw : MutableList<SwitchDetailEntity> = mutableListOf()
                for (sw in mListSW){
                    if (switch.id.equals(sw.idSwitch)){
                        subSw.add(sw)
                    }
                }
                mList.add(Switch(switch.id, switch.idRoom, switch.name, switch.isChecked, switch.type, subSw, switch.floor, switch.nameRoom))
            }
            adapterSwitch.setDatas(mList)
        })

    }


}