package com.vbeeon.iotdbs.presentation.fragment.switchDetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.presentation.activity.SwitchDetailActivity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchBuildingAdapter
import com.vbeeon.iotdbs.presentation.adapter.SwitchDetailAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.dialog.DetailSwitchBottomDialog
import com.vbeeon.iotdbs.presentation.dialog.ListRoomBottomDialog
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
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
    lateinit var adapterSwitch: SwitchDetailAdapter
    var switchId: String = ""
    var switchName: String = ""
    lateinit var modalbottomSheetFragment: ListRoomBottomDialog
    lateinit var detalbottomSheetFragment: DetailSwitchBottomDialog

    companion object {
        fun newInstance(id: String, name: String): SwitchDetailFragment {
            val fragment = SwitchDetailFragment()
            val args = Bundle()
            args.putString("switch_id", id)
            args.putString("switch_name", name)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("switch_id")?.let {
            switchId = it
        }
        arguments?.getString("switch_name")?.let {
            switchName = it
        }
    }

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
        tv_toolbar_title.text = ""
        imgBack.visibility = View.VISIBLE
        initRcvSwitch()
        Timber.e("" + switchId + switchName)

        modalbottomSheetFragment = ListRoomBottomDialog(switchId, doneClick = {
            Timber.e("dialog dimis")
            modalbottomSheetFragment.dismiss()
        })
        tvLocationRoom.setOnSafeClickListener {
            //modalbottomSheetFragment.show(childFragmentManager,modalbottomSheetFragment.tag)
        }
//        detalbottomSheetFragment = DetailSwitchBottomDialog(switchId, doneClick = {
//            detalbottomSheetFragment.dismiss()
//        })
    }

    private fun initRcvSwitch() {
        adapterSwitch = context?.let {
            SwitchDetailAdapter(it, itemClick = {
                Timber.d("click item framgment")

            })
        }!!
        rcvListSwitchDetal.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvListSwitchDetal.apply { adapter = adapterSwitch }

        val switchOne: MutableList<SwitchDetailEntity> = ArrayList()
//        switchOne.add(SwitchDetailEntity(0, 0, "Công tắc 1", true))
//        switchOne.add(SwitchDetailEntity(1, 0, "Công tắc 2", false))
//        switchOne.add(SwitchDetailEntity(2, 0, "Công tắc 3", false))
        //adapterSwitch.setDatas(switchOne)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadDataSubSwitch(this, switchId)
    }


    override fun observable() {
        mainViewModel.subSwRespon.observe(this, Observer {
            //create switch
            Timber.d("list " + it.size)
            adapterSwitch.setDatas(it)
        })

    }


}