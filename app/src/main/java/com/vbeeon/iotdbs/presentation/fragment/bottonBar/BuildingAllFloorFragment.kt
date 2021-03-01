package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.switchDetail.SwitchDetailFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_building_all_floor.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class BuildingAllFloorFragment : BaseFragment() {
    val mListSwitch1: MutableList<SwitchEntity> = ArrayList()
    val mListSwitch2: MutableList<SwitchEntity> = ArrayList()
    val mListSW: MutableList<SwitchDetailEntity> = ArrayList()
    val mList1: MutableList<Switch> = ArrayList()
    val mList2: MutableList<Switch> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterSwitch1: SwitchBuildingAdapter
    lateinit var adapterSwitch2 : SwitchBuildingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onResume() {
        super.onResume()
        Timber.e("resume")
        // mainViewModel.exeGetStateFromRemote1()
      //  mainViewModel.loadAllDataSwitch(this)
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_building_all_floor
    }

    override fun initView() {
        tvNhietDo1.text = "23 " + 0x00B0.toChar()+"C"
        tvNhietDo2.text = "25 " + 0x00B0.toChar()+"C"
        Glide.with(this).load(R.drawable.img_build_all).into(imgCoverBuildingAll)
        adapterSwitch1 = context?.let { SwitchBuildingAdapter(it, doneClick = {
            (context as MainActivity).launchActivity<SwitchDetailActivity>{
                putExtra(ConstantCommon.KEY_SEND_SWICH_ID, mListSwitch1[it].id)
                putExtra(ConstantCommon.KEY_SEND_SWICH_NAME, mListSwitch1[it].name)
                putExtra(ConstantCommon.KEY_SEND_SWICH_FLOOR, mListSwitch1[it].floor)
            }
        }) }!!
        rcvListSWFloor1.layoutManager = GridLayoutManager(context, 2)
        rcvListSWFloor1.apply { adapter = adapterSwitch1 }
       // recyclerView.layoutManager = LinearLayoutManager(this)
        rcvListSWFloor1.isNestedScrollingEnabled = false
        initRcvSwitch()

    }

    private fun initRcvSwitch() {
        adapterSwitch2 = activity?.let {
            SwitchBuildingAdapter(it, doneClick = {
//                (context as MainActivity).
//                openFragment(SwitchDetailFragment.newInstance(mListSwitch[it].id,mListSwitch[it].name ), true)
                (context as MainActivity).launchActivity<SwitchDetailActivity>{
                    putExtra(ConstantCommon.KEY_SEND_SWICH_ID, mListSwitch2[it].id)
                    putExtra(ConstantCommon.KEY_SEND_SWICH_NAME, mListSwitch2[it].name)
                    putExtra(ConstantCommon.KEY_SEND_SWICH_FLOOR, mListSwitch2[it].floor)
                }
            })
        }!!
        rcvListSWFloor2.layoutManager = GridLayoutManager(context, 2)
        rcvListSWFloor2.apply { adapter = adapterSwitch2 }
        rcvListSWFloor2.isNestedScrollingEnabled = false
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadAllDataSwitch(this)

    }



    override fun observable() {
        mainViewModel.swRespon.observe(this, Observer {
            mListSwitch2.clear()
            mListSwitch1.clear()
            for (switch in it){
                if (switch.idRoom<7){
                    mListSwitch1.add(switch)
                }else
                    mListSwitch2.add(switch)
            }
            Timber.e(""+mListSwitch1.size +":"+mListSwitch2.size)
            mainViewModel.loadAllSubSwitch(this)
        })
        mainViewModel.switchRespon.observe(this, Observer {
            mList1.clear()
            mList2.clear()
            mListSW.clear()
            mListSW.addAll(it)
            Timber.e("size sw="+it.size)
            for (switch in mListSwitch1){
                var subSw : MutableList<SwitchDetailEntity> = mutableListOf()
                for (sw in mListSW){
                    if (switch.id.equals(sw.idSwitch)){
                        subSw.add(sw)
                    }
                }
                mList1.add(Switch(switch.id, switch.idRoom, switch.name, switch.isChecked, switch.type, subSw, switch.floor, switch.nameRoom, 1))
            }
            for (switch in mListSwitch2){
                var subSw : MutableList<SwitchDetailEntity> = mutableListOf()
                for (sw in mListSW){
                    if (switch.id.equals(sw.idSwitch)){
                        subSw.add(sw)
                    }
                }
                mList2.add(Switch(switch.id, switch.idRoom, switch.name, switch.isChecked, switch.type, subSw, switch.floor, switch.nameRoom, 1))
            }
            adapterSwitch1.setDatas(mList1)
            adapterSwitch2.setDatas(mList2)
        })

    }


}