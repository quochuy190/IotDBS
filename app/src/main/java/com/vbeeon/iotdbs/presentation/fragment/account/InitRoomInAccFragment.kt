package com.vbeeon.iotdbs.presentation.fragment.account

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity
import com.vbeeon.iotdbs.data.model.Floor
import com.vbeeon.iotdbs.presentation.adapter.FloorChoseAdapter
import com.vbeeon.iotdbs.presentation.adapter.RoomDialogAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.presentation.fragment.bottonBar.BuildingFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.view.CircularSeekBar
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import com.vbeeon.iotdbs.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.botton_sheet_dialog_select_room.*
import kotlinx.android.synthetic.main.fragment_init_room_in_acc.*
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*
import timber.log.Timber


@Suppress("DEPRECATION")
class InitRoomInAccFragment : BaseFragment() {
    val mListRoomF1: MutableList<RoomEntity> = ArrayList()
    val mListRoomF2: MutableList<RoomEntity> = ArrayList()
    val mListFloor: MutableList<Floor> = ArrayList()
    lateinit var adapterRoom : FloorChoseAdapter
    lateinit var mViewModel: UserViewModel
    var phone : String = ""
    var pass : String  = ""
    companion object {
        fun newInstance(phone: String, pass: String): InitRoomInAccFragment {
            val fragment = InitRoomInAccFragment()
            val args = Bundle()
            args.putString("phone", phone)
            args.putString("pass", pass)
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("phone")?.let {
            phone = it
        }
        arguments?.getString("pass")?.let {
            pass = it
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_init_room_in_acc
    }

    override fun initView() {
        adapterRoom = context?.let { FloorChoseAdapter(it, doneClick = {
            // doneClick(0)
        }) }!!
        rcvFloor.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        rcvFloor.apply { adapter = adapterRoom }
        btnRegister.setOnSafeClickListener {
            val gson = Gson()
            val json = gson.toJson(mListFloor)
            Timber.e(""+json)
            mViewModel.insertUserAdmin(UserEntity(0, phone, pass, "", json, 0, 0))
        }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mViewModel.loadAllData(this)
    }

    override fun observable() {
        mViewModel.loadRoomRes.observe(this, Observer {
            mListRoomF1.clear()
            mListRoomF2.clear()
            for (room in it){
                room.isSelected = false
                if (room.floor==1){
                    mListRoomF1.add(room)
                }else if (room.floor ==2)
                    mListRoomF2.add(room)
            }
            mListFloor.add(Floor(0, "Tầng 1", mListRoomF1))
            mListFloor.add(Floor(1, "Tầng 2", mListRoomF2))
            adapterRoom.setDatas(mListFloor)
        })
    }


}