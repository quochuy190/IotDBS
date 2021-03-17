package com.vbeeon.iotdbs.presentation.fragment.script

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.vbeeon.iotdbs.presentation.activity.ScriptActivity
import com.vbeeon.iotdbs.presentation.activity.SwitchDetailActivity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.ScriptAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import com.vbeeon.iotdbs.viewmodel.ScripViewModel
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_list_script.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ListSciptFragment : BaseFragment() {

    val mListScript: MutableList<ScriptEntity> = ArrayList()
    lateinit var mainViewModel: ScripViewModel
    lateinit var adapterScript: ScriptAdapter
    var position: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_script
    }

    override fun initView() {
        adapterScript = context?.let {
            ScriptAdapter(it, doneClick = {
                position = it
                if (mListScript[it].description.equals("1")) {
                    mainViewModel.exeControlGroup1(mListScript[it].control, mListScript[it].id.toString())
                } else if (mListScript[it].description.equals("2")) {
                    mainViewModel.exeControlGroup2(mListScript[it].control, mListScript[it].id.toString())
                }
            }, deleteItem = {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setMessage("Bạn có chắc chắn muốn xóa ngữ cảnh này không?")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, id ->
                        mainViewModel.deleteScript(it)
                    })
                    .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                val alert: AlertDialog = builder.create()
                alert.show()
            })
        }!!
        rcvListScript.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListScript.apply { adapter = adapterScript }
        // recyclerView.layoutManager = LinearLayoutManager(this)

        fbtnAddScript.setOnSafeClickListener {
            (context as MainActivity).launchActivity<ScriptActivity> {

            }
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(ScripViewModel::class.java)
        mainViewModel.loadDataScript(this)
    }

    override fun observable() {
        mainViewModel.scriptsRes.observe(this, Observer {
            mListScript.clear()
            if (it.size > 0) {
                mListScript.addAll(it)
                adapterScript.setDatas(mListScript)
                tvScripNull.visibility = View.GONE
            } else {
                Timber.e("load null")
                tvScripNull.visibility = View.VISIBLE
            }
        })
        mainViewModel.resControlAll.observe(this, Observer {
            if (it) {
                mListScript[position].control = 1
            } else {
                mListScript[position].control = 0
            }
            adapterScript.notifyDataSetChanged()
        })
    }


}