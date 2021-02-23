package com.vbeeon.iotdbs.presentation.fragment.script

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.presentation.activity.ScriptActivity
import com.vbeeon.iotdbs.presentation.activity.SwitchDetailActivity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.ScriptAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_list_script.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ListSciptFragment : BaseFragment() {

    val mListScript: MutableList<ScriptEntity> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterScript : ScriptAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_script
    }

    override fun initView() {
        adapterScript = context?.let { ScriptAdapter(it, doneClick = {

        }) }!!
        rcvListScript.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        rcvListScript.apply { adapter = adapterScript }
        // recyclerView.layoutManager = LinearLayoutManager(this)

        fbtnAddScript.setOnSafeClickListener {
            (context as MainActivity).launchActivity<ScriptActivity>{
            }
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadDataScript(this)
    }

    override fun observable() {
        mainViewModel.scriptsRes.observe(this, Observer {
            mListScript.clear()
            mListScript.addAll(it)
            adapterScript.setDatas(mListScript)
        })
    }


}