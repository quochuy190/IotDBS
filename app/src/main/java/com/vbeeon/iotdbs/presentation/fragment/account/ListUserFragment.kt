package com.vbeeon.iotdbs.presentation.fragment.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity
import com.vbeeon.iotdbs.presentation.activity.AccountActivity
import com.vbeeon.iotdbs.presentation.activity.ScriptActivity
import com.vbeeon.iotdbs.presentation.activity.SwitchDetailActivity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.ScriptAdapter
import com.vbeeon.iotdbs.presentation.adapter.UserAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.*
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import com.vbeeon.iotdbs.viewmodel.ScripViewModel
import com.vbeeon.iotdbs.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_list_script.*
import kotlinx.android.synthetic.main.fragment_list_script.fbtnAddScript
import kotlinx.android.synthetic.main.fragment_list_user.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ListUserFragment : BaseFragment() {

    val mListScript: MutableList<UserEntity> = ArrayList()
    lateinit var mainViewModel: UserViewModel
    lateinit var adapterScript : UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_user
    }

    override fun initView() {
        adapterScript = context?.let { UserAdapter(it, doneClick = {
            (context as AccountActivity).openFragment(UserDetailFragment.newInstance(mListScript[it].name, mListScript[it].listRoom), true)
        }) }!!
        rcvListUser.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        rcvListUser.apply { adapter = adapterScript }
        // recyclerView.layoutManager = LinearLayoutManager(this)

        fbtnAddScript.setOnSafeClickListener {
            (context as AccountActivity).openFragment(InitRoomInAccFragment.newInstance(), true)
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mainViewModel.loadAllUser(this)
    }

    override fun observable() {
        mainViewModel.loadUserRes.observe(this, Observer {
            if (it.size>0){
                mListScript.clear()
                mListScript.addAll(it)
                adapterScript.setDatas(mListScript)
                tvUserNull.gone()
            }else{
                Timber.e("load null")
                tvUserNull.visible()
            }
        })
    }


}