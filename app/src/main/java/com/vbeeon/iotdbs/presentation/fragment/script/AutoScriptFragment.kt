package com.vbeeon.iotdbs.presentation.fragment.script

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.presentation.adapter.AutoScriptAdapter
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import com.vbeeon.iotdbs.viewmodel.ScripViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class AutoScriptFragment : BaseFragment() {

    lateinit var mViewModel: ScripViewModel
    val mList: MutableList<ScriptEntity> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterScipt: AutoScriptAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_recycleview
    }

    override fun initView() {
        adapterScipt = context?.let {
            AutoScriptAdapter(it, doneClick = {
            })
        }!!
        rcvAutoScript.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvAutoScript.apply { adapter = adapterScipt }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ScripViewModel::class.java)
        val isInsert = SharedPrefs.instance.get(ConstantCommon.KEY_INSERT_AUTO_SCRIPT, Boolean::class.java)
        if (isInsert){
            mViewModel.loadDataScriptAuto(this)
        }else {
            mViewModel.insertScript()
            Thread.sleep(500)
            mViewModel.loadDataScriptAuto(this)
        }
        mViewModel.scriptsRes.observe(this, Observer {
            adapterScipt.setDatas(it)
        })
    }

    override fun observable() {

    }


}