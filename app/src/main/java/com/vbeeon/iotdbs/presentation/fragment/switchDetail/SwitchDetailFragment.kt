package com.vbeeon.iotdbs.presentation.fragment.switchDetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchBuildingAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchDetailAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_switch_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class SwitchDetailFragment : BaseFragment() {
    val mListSwitch: MutableList<SwitchDetailEntity> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterSwitch : SwitchDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_switch_detail
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Tòa nhà"
        imgBack.visibility = View.INVISIBLE
        initRcvSwitch()

    }

    private fun initRcvSwitch() {
        adapterSwitch = activity?.let {
            SwitchDetailAdapter(it, doneClick = {

            })
        }!!
        rcvListSwitchDetal.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        rcvListSwitchDetal.apply { adapter = adapterSwitch }

        val switchOne: MutableList<SwitchDetailEntity> = ArrayList()
        switchOne.add(SwitchDetailEntity(0, 0, "Công tắc 1", true))
        switchOne.add(SwitchDetailEntity(1, 0, "Công tắc 2", false))
        switchOne.add(SwitchDetailEntity(2, 0, "Công tắc 3", false))
        adapterSwitch.setDatas(switchOne)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }



    override fun observable() {
        mainViewModel.devicesRes.observe(this, Observer {
            //create switch
        })

    }


}