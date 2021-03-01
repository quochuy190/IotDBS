package com.vbeeon.iotdbs.presentation.fragment.login

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_intro.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConfigNetwork
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.*


@Suppress("DEPRECATION")
class IntroduceFragment : BaseFragment() {
    lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_intro
    }

    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvIntro.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        initEvent()
    }

    private fun initEvent() {
        btnStart.setOnSafeClickListener {
            SharedPrefs.instance.put(ConstantCommon.IS_FIRST_OPEN_APP, true)
            (context as LoginActivity).openFragment(LoginFragment(), true)
        }
    }

    override fun initViewModel() {
        btnStart.isEnabled = false
        btnStart.background.alpha = 50
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initDataLocal();
        mViewModel.loading.observeForever(this::showProgressDialog)
        mViewModel.error.observeForever({ throwable ->
            Timber.e("error"+throwable.message)
            btnStart.isEnabled = true
            btnStart.background.alpha = 250
        })
        mViewModel.resCreateGr.observe(this, androidx.lifecycle.Observer {
            Timber.e("error")
//            if (it)
//                this.launchActivity<LoginActivity>()
            btnStart.isEnabled = true
            btnStart.background.alpha = 250
        })
    }

    override fun observable() {

    }

    private fun initDataLocal() {
        val roomList: MutableList<RoomEntity> = ArrayList()
        roomList.add(RoomEntity(0, "Phòng tổng hợp", 1, true))
        roomList.add(RoomEntity(1, "Phòng làm việc", 1, false))
        roomList.add(RoomEntity(2, "Phòng họp 1", 1, false))
        roomList.add(RoomEntity(3, "Phòng họp 3", 1, false))
        //  roomList.add(RoomEntity(4,"Phòng phó gián đốc 1",1,  false))
        //  roomList.add(RoomEntity(5,"Phòng phó gián đốc 2",1,  false))
        roomList.add(RoomEntity(6, "Phòng Pantry", 1, false))
        // floor 2
        roomList.add(RoomEntity(7, "Phòng họp", 2, true))
        //  roomList.add(RoomEntity(8,"Phòng giám đốc",2,  false))
        roomList.add(RoomEntity(9, "Phòng làm việc", 2, false))
        roomList.add(RoomEntity(10, "Phòng Pantry", 2, false))
        mViewModel.insert(roomList)
        Thread.sleep(500)
        val roomSwitch: MutableList<SwitchEntity> = ArrayList()
        roomSwitch.add(SwitchEntity("SW00568", 0, "P.TH", true, 1, 1, "Phòng tổng hợp"))
        roomSwitch.add(SwitchEntity("SW00590", 0, "Cảm biến nhiệt độ, độ ẩm T1", false, 4, 1, "Phòng tổng hợp"))
        roomSwitch.add(SwitchEntity("SW00584", 1, "P.Làm việc T1 trên", true, 2, 1, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00581", 1, "P.Làm việc T1 dưới", true, 2, 1, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00571", 2, "P.Họp 1", true, 2, 1, "Phòng hợp 1"))
        roomSwitch.add(SwitchEntity("SW00570", 3, "P.Họp 3", true, 2, 1, "Phòng họp 3"))
        // roomSwitch.add(SwitchEntity("SW00574",4,"P.Phó giám đốc", true, 2, 1, "Phòng phó giám đốc"))
        //roomSwitch.add(SwitchEntity("SW00585",5,"P.Phó giám đốc", true, 2, 1, "Phòng phó giám đốc"))
        roomSwitch.add(SwitchEntity("SW00582", 6, "P.Pantry", true, 2, 1, "Phòng Pantry"))
        roomSwitch.add(SwitchEntity("SW00580", 7, "P.Họp", true, 3, 2, "Phòng họp"))
        //  roomSwitch.add(SwitchEntity("SW00577",8,"P.Giám đốc", true, 2, 2, "Phòng giám đốc"))
        roomSwitch.add(SwitchEntity("SW00572", 9, "P.Làm việc T2", true, 2, 2, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00575", 9, "P.Làm việc T2", true, 3, 2, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00591", 9, "Cảm biến nhiệt độ, độ ẩm T2", true, 4, 2, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00578", 10, "P.Pantry T2", true, 2, 2, "Phòng Pantry"))
        mViewModel.insertSwitch(roomSwitch)
        Thread.sleep(500)
        val subSwitch: MutableList<SwitchDetailEntity> = ArrayList()
        subSwitch.add(SwitchDetailEntity("SW00568sw1", "SW00568", "Đèn", "sw2", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00584sw1", "SW00584", "Dãy trong", "sw1", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00584sw3", "SW00584", "Dãy giữa", "sw3", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00581sw1", "SW00581", "Dãy cửa", "sw1", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00581sw3", "SW00581", "Quạt gió", "sw3", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00571sw1", "SW00571", "Quạt gió", "sw1", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00571sw3", "SW00571", "Đèn", "sw3", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00570sw1", "SW00570", "Quạt gió", "sw1", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00570sw3", "SW00570", "Đèn", "sw3", false, 0, 1))
        // subSwitch.add(SwitchDetailEntity("SW00574sw1", "SW00574", "Quạt gió", "sw1", true, 1, 1))
        // subSwitch.add(SwitchDetailEntity("SW00574sw3", "SW00574", "Đèn", "sw3", true, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00582sw1", "SW00582", "Quạt gió", "sw1", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00582sw3", "SW00582", "Đèn", "sw3", false, 0, 1))
        //  subSwitch.add(SwitchDetailEntity("SW00585sw3", "SW00585", "Quạt gió", "sw3", true, 1, 1))
        //  subSwitch.add(SwitchDetailEntity("SW00585sw1", "SW00585", "Đèn", "sw1", true, 0, 1))
        //floor2
        subSwitch.add(SwitchDetailEntity("SW00580sw1", "SW00580", "Kho", "sw1", false, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00580sw2", "SW00580", "Quạt gió", "sw2", false, 1, 2))
        subSwitch.add(SwitchDetailEntity("SW00580sw3", "SW00580", "Đèn", "sw3", false, 0, 2))
        // subSwitch.add(SwitchDetailEntity("SW00577sw1", "SW00577", "Quạt thông gió", "sw1", true, 1, 2))
        //  subSwitch.add(SwitchDetailEntity("SW00577sw3", "SW00577", "Đèn", "sw3", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00572sw1", "SW00572", "Đèn dãy trong", "sw1", false, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00572sw3", "SW00572", "Đèn dãy giữa", "sw3", false, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00575sw1", "SW00575", "Đèn IoT", "sw1", false, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00575sw2", "SW00575", "Quạt thông gió", "sw2", false, 1, 2))
        subSwitch.add(SwitchDetailEntity("SW00575sw3", "SW00575", "Đèn hành lang T2", "sw3", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00578sw1", "SW00578", "Nút 1", "sw1", false, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00578sw3", "SW00578", "Đèn", "sw3", false, 0, 2))
        mViewModel.insertSubSwitch(subSwitch)
        Thread.sleep(500)

        var mListSWFl2: MutableList<String> = mutableListOf()
        var mListSWFl1: MutableList<String> = mutableListOf()
        for (sw in subSwitch) {
            if (sw.floor == 1) {
                mListSWFl1.add(
                    "/" + ConfigNetwork.mIoTServerFloor1 + "/" + ConfigNetwork.mIoTServerFloor1 + "/" + ConfigNetwork.mIoTServerNameFloor1 + "/" + sw.idSwitch + "/" + sw.sortName + "/report/la"
                )
            } else {
                mListSWFl2.add(
                    "/" + ConfigNetwork.mIoTServerFloor2 + "/" + ConfigNetwork.mIoTServerFloor2 + "/" + ConfigNetwork.mIoTServerNameFloor2 + "/" + sw.idSwitch + "/" + sw.sortName + "/report/la"
                )
            }
        }
        mViewModel.exeCreateGroupRemote(mListSWFl1, mListSWFl2)
    }

}