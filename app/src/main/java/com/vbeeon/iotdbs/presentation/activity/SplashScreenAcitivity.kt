package com.vbeeon.iotdbs.presentation.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.presentation.base.BaseActivity
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import com.vbeeon.iotdbs.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splashscreen.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.*

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 21:03
 * Version: 1.0
 */
class SplashScreenAcitivity : BaseActivity(){
    lateinit var mViewModel : SplashViewModel
    override fun provideLayoutId(): Int {
       return R.layout.activity_splashscreen
        //
    }

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Glide.with(this).load(R.drawable.splash_screen).into(backGround)
        if (checkPermissionApp(this)) {
            goToNextScreen()
        } else {
            showDialogPermission(this)
        }
        if (intent.extras != null) {
            try {
                Timber.e("onCreate: " + intent.extras)
                val bundle = intent.extras
                Timber.e("onCreate: " + bundle)
                if (intent.extras!!.getBundle("data") != null)
                    Timber.e("onCreate: " + intent.extras!!.getBundle("data"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else
            Timber.e("onCreate: null" )
    }

    private fun requestPermission(context: Context) {
        val PERMISSIONS = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            PERMISSIONS.add(Manifest.permission.CAMERA)
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!PERMISSIONS.isEmpty()) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                PERMISSIONS.toTypedArray(),
                MY_PERMISSIONS_REQUEST_PERMISSION
            )
        }
    }


    private fun checkPermissionApp(context: Context): Boolean {
        return true
//        if (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_PHONE_STATE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    (context as Activity),
//                    Manifest.permission.READ_PHONE_STATE
//                )
//            ) {
//                return false
//            }
//        }
//        if (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.CAMERA
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return false
//        }
//        return if (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            !ActivityCompat.shouldShowRequestPermissionRationale(
//                (context as Activity),
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//        } else true
    }

    private fun goToNextScreen() {
      /*  if (checkPermissionApp(this)) {
            Handler().postDelayed({ *//* Create an Intent that will start the Menu-Activity. *//*
                val mainIntent: Intent
                val isFirs = SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
                if (isFirs) {
                    this.launchActivity<MainActivity>()
                } else {
                    this.launchActivity<MainActivity>()
                }
                finish()
            }, 1000)
        }*/

        Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val isFirs = SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
            if (!isFirs) {
                initDataLocal();
                this.launchActivity<LoginActivity>()
            } else {
                this.launchActivity<MainActivity>()
            }
            finish()
        }, 2000)
    }

    private fun initDataLocal() {
        val roomList: MutableList<RoomEntity> = ArrayList()

        roomList.add(RoomEntity(0,"Phòng tổng hợp", 1,true))
        roomList.add(RoomEntity(1,"Phòng làm việc", 1, false))
        roomList.add(RoomEntity(2,"Phòng họp 1", 1, false))
        roomList.add(RoomEntity(3,"Phòng họp 3",1,  false))
        roomList.add(RoomEntity(4,"Phòng phó gián đốc 1",1,  false))
        roomList.add(RoomEntity(5,"Phòng phó gián đốc 2",1,  false))
        roomList.add(RoomEntity(6,"Phòng ăn",1,  false))
        // floor 2
        roomList.add(RoomEntity(7,"Phòng họp",2,  true))
        roomList.add(RoomEntity(8,"Phòng giám đốc",2,  false))
        roomList.add(RoomEntity(9,"Phòng làm việc",2,  false))
        roomList.add(RoomEntity(10,"Phòng ăn",2,  false))
        mViewModel.insert(roomList)
        Thread.sleep(500)
        val roomSwitch: MutableList<SwitchEntity> = ArrayList()
        roomSwitch.add(SwitchEntity("SW00568",0,"P.TH", true, 1, 1, "Phòng tổng hợp"))
        roomSwitch.add(SwitchEntity("SW00590",0,"Cảm biến nhiệt độ, độ ẩm T1", false, 4, 1, "Phòng tổng hợp"))
        roomSwitch.add(SwitchEntity("SW00584",1,"P.Làm việc T1 trên", true, 2, 1, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00581",1,"P.Làm việc T1 dưới", true, 2, 1, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00571",2,"P.Họp 1", true, 2, 1, "Phòng hợp 1"))
        roomSwitch.add(SwitchEntity("SW00570",3,"P.Họp 3", true, 2, 1, "Phòng họp 3"))
        roomSwitch.add(SwitchEntity("SW00574",4,"P.Phó giám đốc", true, 2, 1, "Phòng phó giám đốc"))
        roomSwitch.add(SwitchEntity("SW00585",5,"P.Phó giám đốc", true, 2, 1, "Phòng phó giám đốc"))
        roomSwitch.add(SwitchEntity("SW00582",6,"P.Pantry", true, 2, 1, "Phòng ăn"))

        roomSwitch.add(SwitchEntity("SW00580",7,"P.Họp", true, 3, 2, "Phòng họp"))
        roomSwitch.add(SwitchEntity("SW00577",8,"P.Giám đốc", true, 2, 2, "Phòng giám đốc"))
        roomSwitch.add(SwitchEntity("SW00572",9,"P.Làm việc T2", true, 2, 2, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00575",9,"P.Làm việc T2", true, 3, 2, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00591",9,"Cảm biến nhiệt độ, độ ẩm T2", true, 4, 2, "Phòng làm việc"))
        roomSwitch.add(SwitchEntity("SW00578",10,"P.Pantry T2", true, 2, 2, "Phòng ăn T2"))
        mViewModel.insertSwitch(roomSwitch)
        Thread.sleep(500)
        val subSwitch: MutableList<SwitchDetailEntity> = ArrayList()
        subSwitch.add(SwitchDetailEntity("SW00568sw1", "SW00568", "Đèn", "/si/si/sitang1/SW00568/sw1/report/la", true, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00584sw1", "SW00584", "Dãy trong", "/si/si/sitang1/SW00584/sw1/report/la", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00584sw3", "SW00584", "Dãy giữa", "/si/si/sitang1/SW00584/sw3/report/la", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00581sw1", "SW00581", "Dãy cửa", "/si/si/sitang1/SW00581/sw1/report/la", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00581sw3", "SW00581", "Quạt gió", "/si/si/sitang1/SW00581/sw3/report/la", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00571sw1", "SW00571", "Quạt gió", "/si/si/sitang1/SW00571/sw1/report/la", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00571sw3", "SW00571", "Đèn", "/si/si/sitang1/SW00571/sw3/report/la", false, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00570sw1", "SW00570", "Quạt gió", "/si/si/sitang1/SW00570/sw1/report/la", false, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00570sw3", "SW00570", "Đèn", "/si/si/sitang1/SW00570/sw3/report/la", true, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00574sw1", "SW00574", "Quạt gió", "/si/si/sitang1/SW00574/sw1/report/la", true, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00574sw3", "SW00574", "Đèn", "/si/si/sitang1/SW00574/sw3/report/la", true, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00582sw1", "SW00582", "Quạt gió", "/si/si/sitang1/SW00582/sw1/report/la", true, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00582sw3", "SW00582", "Đèn", "/si/si/sitang1/SW00582/sw3/report/la", true, 0, 1))
        subSwitch.add(SwitchDetailEntity("SW00585sw3", "SW00585", "Quạt gió", "/si/si/sitang1/SW00585/sw3/report/la", true, 1, 1))
        subSwitch.add(SwitchDetailEntity("SW00585sw1", "SW00585", "Đèn", "/si/si/sitang1/SW00585/sw1/report/la", true, 0, 1))
        //floor2
        subSwitch.add(SwitchDetailEntity("SW00580sw1", "SW00580", "Kho", "/si/si/sitang2/SW00580/sw1/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00580sw2", "SW00580", "Quạt gió", "/si/si/sitang2/SW00580/sw2/report/la", true, 1, 2))
        subSwitch.add(SwitchDetailEntity("SW00580sw3", "SW00580", "Đèn", "/si/si/sitang2/SW00580/sw3/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00577sw1", "SW00577", "Quạt thông gió", "/si/si/sitang2/SW00577/sw1/report/la", true, 1, 2))
        subSwitch.add(SwitchDetailEntity("SW00577sw3", "SW00577", "Đèn", "/si/si/sitang2/SW00577/sw3/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00572sw1", "SW00572", "Đèn dãy trong", "/si/si/sitang2/SW00572/sw1/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00572sw3", "SW00572", "Đèn dãy giữa", "/si/si/sitang2/SW00572/sw3/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00575sw1", "SW00575", "Đèn IoT", "/si/si/sitang2/SW00575/sw1/report/la", false, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00575sw2", "SW00575", "Quạt thông gió", "/si/si/sitang2/SW00575/sw2/report/la", false, 1, 2))
        subSwitch.add(SwitchDetailEntity("SW00575sw3", "SW00575", "Đèn hành lang T2", "/si/si/sitang2/SW00575/sw3/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00578sw1", "SW00578", "Nút 1", "/si/si/sitang2/SW00578/sw1/report/la", true, 0, 2))
        subSwitch.add(SwitchDetailEntity("SW00578sw3", "SW00578", "Đèn", "/si/si/sitang2/SW00578/sw3/report/la", true, 0, 2))
        mViewModel.insertSubSwitch(subSwitch)
        Thread.sleep(500)
        mViewModel.exeCreateGroupRemote()

    }

    val MY_PERMISSIONS_REQUEST_PERMISSION = 124

    fun showDialogPermission(context: Context?) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.title_permission)
        alertBuilder.setMessage(R.string.message_permission)
        alertBuilder.setPositiveButton(
            android.R.string.yes
        ) { dialog, which -> requestPermission(this) }
        val alert = alertBuilder.create()
        alert.show()
    }

  /*  fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?, grantResults: IntArray?
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_PERMISSION -> {
                if (grantResults != null && grantResults.size > 0) {
                    var count = 0
                    var i = 0
                    while (i < grantResults.size) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            showDialogNotPermission(this)
                        } else {
                            count++
                        }
                        i++
                    }
                    if (count == grantResults.size) goToNextScreen()
                }
                super.onRequestPermissionsResult(
                    requestCode, permissions!!,
                    grantResults!!
                )
            }
            else -> super.onRequestPermissionsResult(
                requestCode, permissions!!,
                grantResults!!
            )
        }
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_PERMISSION -> {
                if (grantResults != null && grantResults.size > 0) {
                    var count = 0
                    var i = 0
                    while (i < grantResults.size) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            showDialogNotPermission(this)
                        } else {
                            count++
                        }
                        i++
                    }
                    if (count == grantResults.size) goToNextScreen()
                }
                super.onRequestPermissionsResult(
                    requestCode, permissions!!,
                    grantResults!!
                )
            }
            else -> super.onRequestPermissionsResult(
                requestCode, permissions!!,
                grantResults!!
            )
        }
    }

    private fun showDialogNotPermission(context: Context) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.title_permission)
        alertBuilder.setMessage(R.string.message_error_permission)
        alertBuilder.setPositiveButton(R.string.exit,
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                finish()
            })
        val alert = alertBuilder.create()
        alert.show()
    }
}