package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import android.view.View
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class BuildingFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

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

        val roomList: MutableList<RoomEntity> = ArrayList()
        roomList.add(RoomEntity(0,"YOUTUBE", false))


    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}