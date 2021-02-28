package com.vbeeon.iotdbs.presentation.fragment.bottonBar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.BuildConfig
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.model.ItemMenu
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.adapter.ItemMenuAdapter
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchBuildingAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


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
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Cài đặt"
        imgBack.visibility = View.INVISIBLE
        adapterMenu = context?.let {
            ItemMenuAdapter(it, doneClick = {
                if (it==6){
                    SharedPrefs.instance.put(ConstantCommon.IS_LOGIN, false)
                    activity?.finish()
                    activity?.launchActivity<LoginActivity>()
                }
            })
        }!!
        rcvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        rcvMenu.apply { adapter = adapterMenu }
        mList.add(ItemMenu(0,getString(R.string.tvMenu_0), R.drawable.menu_home))
        mList.add(ItemMenu(1,getString(R.string.tvMenu_1), R.drawable.menu_device))
        mList.add(ItemMenu(2,getString(R.string.tvMenu_2), R.drawable.menu_setting))
        mList.add(ItemMenu(3,getString(R.string.tvMenu_3), R.drawable.menu_introduce))
        mList.add(ItemMenu(4,getString(R.string.tvMenu_4), R.drawable.menu_account))
        mList.add(ItemMenu(5,getString(R.string.tvMenu_5), R.drawable.menu_help))
        mList.add(ItemMenu(6,getString(R.string.tvMenu_6), R.drawable.menu_logout))

        adapterMenu.setDatas(mList)
        tvVersion.text = "Version: "+BuildConfig.VERSION_NAME
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}