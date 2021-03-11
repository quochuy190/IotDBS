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
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity
import com.vbeeon.iotdbs.data.model.Floor
import com.vbeeon.iotdbs.data.model.Room
import com.vbeeon.iotdbs.data.model.Switch
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
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class InitRoomInAccFragment : BaseFragment() {
    val mListRoomF1: MutableList<Room> = ArrayList()
    val mListRoom: MutableList<RoomEntity> = ArrayList()
    val mListRoomF2: MutableList<Room> = ArrayList()
    val mListFloor: MutableList<Floor> = ArrayList()
    lateinit var adapterRoom : FloorChoseAdapter
    lateinit var mViewModel: UserViewModel
    var phone : String = ""
    var pass : String  = ""
    companion object {
        fun newInstance(): InitRoomInAccFragment {
            val fragment = InitRoomInAccFragment()
            val args = Bundle()
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_init_room_in_acc
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Chia sẻ thiết bị"
        adapterRoom = context?.let { FloorChoseAdapter(it, doneClick = {
            // doneClick(0)
        }) }!!
        rcvFloor.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        rcvFloor.apply { adapter = adapterRoom }
        btnRegister.setOnSafeClickListener {
            if (isValidate()){
                var sJson : String = ""
                for (floor in mListFloor){
                    for (room in floor.listRoom){
                        if (room.isSelected){
                            for (sw in room.listSW){
                                sJson = sJson + sw.id +"#"
                            }
                        }
                    }
                }
                if (sJson.length>0){
                    phone = edtFullName.text.toString()
                    mViewModel.insertUserAdmin(UserEntity(0, phone, pass, "", sJson +"VBee@2021", 0, 0, 1))
                    showMessage("Tạo tài khoản thành công")
                    activity?.onBackPressed()
                }else{
                    showDialogMessage(context, "Bạn chưa chọn phòng nào để điều khiển!")
                }


            }
        }

    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mViewModel.loadAllData(this)
    }

    override fun observable() {
        mViewModel.loadRoomRes.observe(this, Observer {
            mListRoom.addAll(it)
            mViewModel.loadAllSW(this)
        })
        mViewModel.loadSWRes.observe(this, Observer {
            mListRoomF1.clear()
            mListRoomF2.clear()
            for (room in mListRoom){
                room.isSelected = false
                val lisSW: MutableList<SwitchEntity> = ArrayList()
                for (sw in it){
                    if (sw.idRoom == room.id){
                        lisSW.add(sw)
                    }
                }
                if (lisSW.size>0){
                    if (room.floor==1){
                        mListRoomF1.add(Room(room.id, room.name, room.floor, false , lisSW))
                    }else if (room.floor ==2)
                        mListRoomF2.add(Room(room.id, room.name, room.floor, false , lisSW))
                }

            }
            mListFloor.add(Floor(0, "Tầng 1", mListRoomF1))
            mListFloor.add(Floor(1, "Tầng 2", mListRoomF2))
            adapterRoom.setDatas(mListFloor)
        })
    }
    private fun isValidate(): Boolean {
        if (edtFullName.text.length == 0) {
            showDialogMessage(context, getString(R.string.validate_Username))
            edtFullName.requestFocus()
            edtFullName.setSelection(0)
            return false
        }
        return true
    }

}