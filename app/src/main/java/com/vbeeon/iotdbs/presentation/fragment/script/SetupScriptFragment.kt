package com.vbeeon.iotdbs.presentation.fragment.script

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.presentation.adapter.SwitchListChoseAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_setup_script.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConfigNetwork
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class SetupScriptFragment : BaseFragment() {
    lateinit var mainViewModel: MainViewModel
    val mListSwitch: MutableList<SwitchEntity> = ArrayList()
    val mListSW: MutableList<SwitchDetailEntity> = ArrayList()
    val mList: MutableList<Switch> = ArrayList()
    lateinit var adapterSwitch: SwitchListChoseAdapter
    var floor: Int = 1
    var mListSubSWString: MutableList<String> = ArrayList()
    var nameScript : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    var idScript : Long =0;
    override fun getLayoutRes(): Int {
        return R.layout.fragment_setup_script
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Thiết lập ngữ cảnh"
        imgBack.visibility = View.VISIBLE
        tabFloor.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        floor = 1
                        activity?.let { mainViewModel.loadAllDataSwitchbyFloor(it, 1) }
                    }
                    1 -> {
                        floor = 2
                        activity?.let { mainViewModel.loadAllDataSwitchbyFloor(it, 2) }
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        adapterSwitch = context?.let {
            SwitchListChoseAdapter(it, doneClick = {

            })
        }!!
        rcvSWbyFloor.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvSWbyFloor.apply { adapter = adapterSwitch }

        btnAddScript.setOnSafeClickListener {
            if (edtNameScript.text.length>0){
                nameScript = edtNameScript.text.toString()
                mListSubSWString.clear()
                idScript = System.currentTimeMillis()
                if (floor == 1) {
                    for (sw in mList) {
                        for (swDetail in sw.listSubSw){
                            if (swDetail.isChecked){
                                mListSubSWString.add(
                                    "/" + ConfigNetwork.mIoTServerFloor1 +
                                            "/" + ConfigNetwork.mIoTServerFloor1 +
                                            "/" + ConfigNetwork.mIoTServerNameFloor1 +
                                            "/" + swDetail.idSwitch +
                                            "/" + swDetail.sortName + "/control"
                                )
                            }
                        }
                    }
                    mainViewModel.exeCreateScriptRemoteF1(idScript.toString(), mListSubSWString)
                } else {
                    for (sw in mList) {
                        for (swDetail in sw.listSubSw){
                            if (swDetail.isChecked){
                                mListSubSWString.add(
                                    "/" + ConfigNetwork.mIoTServerFloor1 +
                                            "/" + ConfigNetwork.mIoTServerFloor1 +
                                            "/" + ConfigNetwork.mIoTServerNameFloor1 +
                                            "/" + swDetail.idSwitch +
                                            "/" + swDetail.sortName + "/control"
                                )
                            }
                        }
                    }
                    mainViewModel.exeCreateScriptRemoteF2(idScript.toString(), mListSubSWString)
                }
            }else
                showDialogMessage(context, "Bạn chưa nhập vào tên ngữ cảnh")

        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadAllDataSwitchbyFloor(this, 1)
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
            //mainViewModel.insertScript(ScriptEntity(idScript, ""+floor, nameScript,true, mListSubSWString, 0, 1))
        })
    }

    override fun observable() {
        mainViewModel.resCreateScript.observe(this, Observer {
            showMessage("Tạo ngữ cảnh thành công")
            mainViewModel.insertScript(ScriptEntity(idScript, ""+floor, nameScript,true, mListSubSWString, 0, 1))
            activity?.onBackPressed()
        })
        mainViewModel.swRespon.observe(this, Observer {
            mListSwitch.clear()
            mListSwitch.addAll(it)
            Timber.e("switchs = " + it.size)
            var ids: MutableList<String> = mutableListOf()
            for (switch in it) {
                ids.add(switch.id)
            }
            mainViewModel.loadSubSwitchBySwitchId(this, ids)
        })
        mainViewModel.switchRespon.observe(this, Observer {
            mList.clear()
            mListSW.clear()
            mListSW.addAll(it)
            Timber.e("size sw=" + it.size)
            for (switch in mListSwitch) {
                var subSw: MutableList<SwitchDetailEntity> = mutableListOf()
                for (sw in mListSW) {
                    if (switch.id.equals(sw.idSwitch)) {
                        sw.isChecked = false
                        subSw.add(sw)
                    }
                }
                mList.add(Switch(switch.id, switch.idRoom, switch.name, false, switch.type, subSw, switch.floor, switch.nameRoom, 2))
            }
            adapterSwitch.setDatas(mList)
        })
    }


}