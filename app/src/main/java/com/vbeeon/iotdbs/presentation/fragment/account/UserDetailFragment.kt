package com.vbeeon.iotdbs.presentation.fragment.account

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.visible
import com.vbeeon.iotdbs.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_init_room_in_acc.*
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*


@Suppress("DEPRECATION")
class UserDetailFragment : BaseFragment() {
    lateinit var mViewModel: UserViewModel
    var name : String = ""
    var sJson : String  = ""
    val MY_PERMISSIONS_REQUEST_PERMISSION = 124
    lateinit var bitmap: Bitmap
    companion object {
        fun newInstance(name: String, sJson: String): UserDetailFragment {
            val fragment = UserDetailFragment()
            val args = Bundle()
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
        arguments?.getString("sJson")?.let {
            sJson = it
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_user_detail
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.visible()
        tv_toolbar_title.text = "Thông tin người dùng"
        edtFullNameDetail.text =  Editable.Factory.getInstance().newEditable(name)
        showQRCode(sJson)
        btnShare.setOnSafeClickListener {
            shareQRcode()
        }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mViewModel.loadAllData(this)
    }

    override fun observable() {
        mViewModel.loadRoomRes.observe(this, Observer {

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
    private fun showQRCode(text: String) {
        //val mode: Int = SharedPref.getInstance().getAll(Constant.getInstance().STATUS_PC_MODE, Int::class.java)
        //String text="{\"imei\":\"F84FAD8850E2\",\"mac\":\"00D279E182FE\",\"password\":\"e4f88a273dcfd85f32ea83defb2bcb46\",\"serial\":\"50KD68005074\"}"; // Whatever you need to encode in the QR code
        val multiFormatWriter = MultiFormatWriter()
        try {
            val hintMap: MutableMap<EncodeHintType, Any> = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"
            hintMap[EncodeHintType.MARGIN] = 1 /* default = 4 */
            hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
            val bitMatrix: BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 1000, hintMap)
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(bitMatrix)
            imgQRCodeAcc.setImageBitmap(bitmap)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            // bmp.recycle();
            val bitmapEnd = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    fun shareQRcode(){
        try {
            val bitmapPath: String = MediaStore.Images.Media.insertImage(context?.getContentResolver(), bitmap, "title", null)
            val bitmapUri = Uri.parse(bitmapPath)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            startActivity(Intent.createChooser(intent, "Share"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}