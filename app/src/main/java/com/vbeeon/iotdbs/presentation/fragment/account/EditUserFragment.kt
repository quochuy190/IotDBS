package com.vbeeon.iotdbs.presentation.fragment.account

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder
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
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class EditUserFragment : BaseFragment() {
    val mListRoomF1: MutableList<Room> = ArrayList()
    val mListRoom: MutableList<RoomEntity> = ArrayList()
    val mListRoomF2: MutableList<Room> = ArrayList()
    val mListFloor: MutableList<Floor> = ArrayList()
    lateinit var adapterRoom : FloorChoseAdapter
    lateinit var mViewModel: UserViewModel
    var name : String = ""
    var sJson : String  = ""
    var idUser : Int=0
    companion object {
        fun newInstance(id:Int,name:String,sJson: String): EditUserFragment {
            val fragment = EditUserFragment()
            val args = Bundle()
            args.putInt("id", id)
            args.putString("name", name)
            args.putString("sJson", sJson)
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("name")?.let {
            name = it
        }
        arguments?.getInt("id")?.let {
            idUser = it
        }
        arguments?.getString("sJson")?.let {
            sJson = it
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
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        btnRegister.text = "Cập nhật"
        tv_toolbar_title.text = "Cập nhật người dùng"
        edtFullName.text =  Editable.Factory.getInstance().newEditable(name)
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
                   var userName = edtFullName.text.toString()
                    mViewModel.update(UserEntity(idUser, userName, userName, "", sJson +"VBee@2021", 0, 0, 1))
                    Thread.sleep(500)
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage("Tạo người dùng thành công, bạn có muốn chia sẻ mã đăng nhập ngay không?")
                        .setCancelable(false)
                        .setPositiveButton("Chia sẻ", DialogInterface.OnClickListener { dialog, id ->
                            showQRCode(sJson)
                        })
                        .setNegativeButton("Để sau", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                            activity?.onBackPressed()
                        })
                    val alert: AlertDialog = builder.create()
                    alert.show()
                   // showMessage("Tạo tài khoản thành công")

                }else{
                    showDialogMessage(context, "Bạn chưa chọn phòng nào để điều khiển!")
                }


            }
        }

    }
    private fun showQRCode(text: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val hintMap: MutableMap<EncodeHintType, Any> = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"
            hintMap[EncodeHintType.MARGIN] = 1 /* default = 4 */
            hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
            val bitMatrix: BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 1000, hintMap)
            val barcodeEncoder = BarcodeEncoder()
            var bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            val bitmapPath: String = MediaStore.Images.Media.insertImage(context?.getContentResolver(), bitmap, "title",
                "Mã QR Code đăng nhập sử dụng DBS")
            val bitmapUri = Uri.parse(bitmapPath)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            startActivity(Intent.createChooser(intent, "Mã QR Code đăng nhập sử dụng DBS"))
        } catch (e: WriterException) {
            e.printStackTrace()
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
                        if (sJson.indexOf(sw.id)>-1){
                            room.isSelected = true
                        }
                        lisSW.add(sw)
                    }
                }
                if (lisSW.size>0){
                    if (room.floor==1){
                        mListRoomF1.add(Room(room.id, room.name, room.floor, room.isSelected , lisSW))
                    }else if (room.floor ==2)
                        mListRoomF2.add(Room(room.id, room.name, room.floor, room.isSelected , lisSW))
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