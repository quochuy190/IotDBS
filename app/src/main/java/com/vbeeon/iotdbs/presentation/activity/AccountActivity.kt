package com.vbeeon.iotdbs.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.account.InitRoomInAccFragment
import com.vbeeon.iotdbs.presentation.fragment.account.ListUserFragment
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.replaceFragment
import java.io.ByteArrayOutputStream
import java.util.*


class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(ListUserFragment(), false)

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
            val bitMatrix: BitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200, hintMap)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            //imgAppStore.setImageBitmap(bitmap)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            // bmp.recycle();
            val bitmapEnd = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}