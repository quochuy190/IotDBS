package com.vbeeon.iotdbs.presentation.fragment.switchDetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
import vn.neo.smsvietlott.common.di.util.ConfigNetwork


@Suppress("DEPRECATION")
class SwitchDetailFragment : BaseFragment() {
    //test
    val mListSwitch: MutableList<SwitchDetailEntity> = ArrayList()
    val mListSubSWString: MutableList<String> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var adapterSwitch: SwitchDetailAdapter
    var switchId: String = ""
    var switchName: String = ""
    lateinit var modalbottomSheetFragment: ListRoomBottomDialog
    lateinit var detalbottomSheetFragment: DetailSwitchBottomDialog
    var floor = 0;

    companion object {
        fun newInstance(id: String, name: String, floor: Int): SwitchDetailFragment {
            val fragment = SwitchDetailFragment()
            val args = Bundle()
            args.putString("switch_id", id)
            args.putString("switch_name", name)
            args.putInt("floor", floor)
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
        arguments?.getInt("floor")?.let {
            floor = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        positionSubSW = 0
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
        tvLocationRoom.text = switchName
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
        initEvent()
    }

    var positionSubSW = 0;
    private fun initEvent() {
        clSwitchOnAll.setOnSafeClickListener {
            if (floor == 1)
                mainViewModel.exeControlGroup1(1, switchId + "tang1_control_1")
            else
                mainViewModel.exeControlGroup2(1, switchId + "tang2_control")
        }
        clSwitchOffAll.setOnSafeClickListener {
            if (floor == 1)
                mainViewModel.exeControlGroup1(0, switchId + "tang1_control_1")
            else
                mainViewModel.exeControlGroup2(0, switchId + "tang2_control")
            // mainViewModel.exeControlGroup(0, switchId + "tang2_control")
        }
    }

    private fun initRcvSwitch() {
        adapterSwitch = context?.let {
            SwitchDetailAdapter(it, itemClick = {
                positionSubSW = it
                Timber.d("click item framgment")
                val linkSubSW =
                    mListSwitch[it].idSwitch + "/" + mListSwitch[it].sortName + "/control"
                if (mListSwitch[it].isChecked)
                    if (floor == 1)
                        mainViewModel.exeControlSubSW1(0, linkSubSW)
                    else
                        mainViewModel.exeControlSubSW2(0, linkSubSW)
                else
                    if (floor == 1)
                        mainViewModel.exeControlSubSW1(1, linkSubSW)
                    else
                        mainViewModel.exeControlSubSW2(1, linkSubSW)

            })
        }!!
        rcvListSwitchDetal.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvListSwitchDetal.apply { adapter = adapterSwitch }

        val switchOne: MutableList<SwitchDetailEntity> = ArrayList()
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadDataSubSwitch(this, switchId)
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable ->
//            showDialogMessage(
//                context,
//                throwable.message
//            )
        })
        //   mainViewModel.exeCreateGroupRemoteBySW(switchId)
        mainViewModel.resControlAll.observe(this, Observer {
            if (it){
                Glide.with(this).load(R.drawable.turn_on_on).into(imgSwitchOnAll)
                Glide.with(this).load(R.drawable.turn_off_default).into(imgSwitchOffAll)
                for (sw in mListSwitch){
                    sw.isChecked = true
                }
                adapterSwitch.notifyDataSetChanged()
            }else {
                Glide.with(this).load(R.drawable.turn_off_default).into(imgSwitchOnAll)
                Glide.with(this).load(R.drawable.turn_off_on).into(imgSwitchOffAll)
                for (sw in mListSwitch){
                    sw.isChecked = false
                }
                adapterSwitch.notifyDataSetChanged()
            }
            mainViewModel.updateListSubSW(mListSwitch)
        })
    }


    override fun observable() {
        mainViewModel.resControlSubSW.observe(this, Observer {
            if (it == 1)
                mListSwitch[positionSubSW].isChecked = true
            else
                mListSwitch[positionSubSW].isChecked = false
            adapterSwitch.notifyItemChanged(positionSubSW)
            mainViewModel.updateListSubSW(mListSwitch)
        })
        mainViewModel.subSwRespon.observe(this, Observer {
            //create switch
            Timber.d("list " + it.size)
            mListSubSWString.clear()
            mListSwitch.clear()
            mListSwitch.addAll(it)
            adapterSwitch.setDatas(mListSwitch)
            if (it!=null&&it.size>0){
                if (floor == 1) {
                    for (sw in mListSwitch) {
                        mListSubSWString.add(
                            "/" + ConfigNetwork.mIoTServerFloor1 +
                                    "/" + ConfigNetwork.mIoTServerFloor1 +
                                    "/" + ConfigNetwork.mIoTServerNameFloor1 +
                                    "/" + sw.idSwitch +
                                    "/" + sw.sortName + "/control"
                        )
                    }
                    mainViewModel.exeCreateScriptRemoteF1(switchId + "tang1_control_1", mListSubSWString)
                } else {
                    for (sw in mListSwitch) {
                        mListSubSWString.add(
                            "/" + ConfigNetwork.mIoTServerFloor2 +
                                    "/" + ConfigNetwork.mIoTServerFloor2 +
                                    "/" + ConfigNetwork.mIoTServerNameFloor2 +
                                    "/" + sw.idSwitch +
                                    "/" + sw.sortName + "/control"
                        )
                    }
                    mainViewModel.exeCreateScriptRemoteF2(switchId + "tang2_control", mListSubSWString)
                }
            }
        })

    }


}