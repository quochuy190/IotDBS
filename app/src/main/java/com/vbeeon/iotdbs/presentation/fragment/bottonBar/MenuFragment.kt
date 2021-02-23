package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.BuildConfig
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.model.ItemMenu
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.presentation.adapter.ItemMenuAdapter
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchBuildingAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_menu.*


@Suppress("DEPRECATION")
class MenuFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    val mList: MutableList<ItemMenu> = ArrayList()
    lateinit var adapterMenu : ItemMenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_menu
    }

    override fun initView() {
        adapterMenu = context?.let {
            ItemMenuAdapter(it, doneClick = {

            })
        }!!
        rcvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        rcvMenu.apply { adapter = adapterMenu }
        mList.add(ItemMenu(0,getString(R.string.tvMenu_0), R.drawable.ic_building))
        mList.add(ItemMenu(1,getString(R.string.tvMenu_1), R.drawable.ic_menu))
        mList.add(ItemMenu(2,getString(R.string.tvMenu_2), R.drawable.ic_menu))
        mList.add(ItemMenu(3,getString(R.string.tvMenu_3), R.drawable.ic_menu))
        mList.add(ItemMenu(4,getString(R.string.tvMenu_4), R.drawable.ic_menu))
        mList.add(ItemMenu(5,getString(R.string.tvMenu_5), R.drawable.ic_menu))
        mList.add(ItemMenu(6,getString(R.string.tvMenu_6), R.drawable.ic_menu))

        adapterMenu.setDatas(mList)
        tvVersion.text = "Version: "+BuildConfig.VERSION_NAME
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}