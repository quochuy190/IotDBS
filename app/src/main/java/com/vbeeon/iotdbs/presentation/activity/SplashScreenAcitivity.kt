package com.vbeeon.iotdbs.presentation.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseActivity
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.viewmodel.LoginViewModel
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
class SplashScreenAcitivity : BaseActivity() {
    lateinit var mViewModel: LoginViewModel
    override fun provideLayoutId(): Int {
        return R.layout.activity_splashscreen
        //
    }

    override fun setupView(savedInstanceState: Bundle?) {
      //  requestPermission(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Glide.with(this).load(R.drawable.bg_login).into(backGround)
        if (checkPermissionApp(this)) {
            goToNextScreen()
        } else {
            showDialogPermission(this)
        }
        initViewModel()
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
            Timber.e("onCreate: null")
    }
    var start : Boolean= false
    private fun initViewModel() {
        mViewModel.loading.observeForever(this::showProgressDialog)
        mViewModel.error.observeForever({ throwable ->
            if (!start){
                start = true
                // showDialogMessage(this,            getString(R.string.system_error))
                Timber.e("error")
             //   this.launchActivity<LoginActivity>()
            }

        })
        mViewModel.resCreateGr.observe(this, androidx.lifecycle.Observer {
            Timber.e("error")
            if (it)
                this.launchActivity<LoginActivity>()
        })
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
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            !ActivityCompat.shouldShowRequestPermissionRationale(
                (context as Activity),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else true
    }

    private fun goToNextScreen() {
        Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val isFirs =
                SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
            val isLogin =
                SharedPrefs.instance.get(ConstantCommon.IS_LOGIN, Boolean::class.java)
            if (!isFirs||!isLogin) {
                this.launchActivity<LoginActivity>()
            } else {
                this.launchActivity<MainActivity>()
            }
            finish()
        }, 1000)
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

//    fun onRequestPermissionsResult(
//          requestCode: Int,
//          permissions: Array<String?>?, grantResults: IntArray?
//      ) {
//          when (requestCode) {
//              MY_PERMISSIONS_REQUEST_PERMISSION -> {
//                  if (grantResults != null && grantResults.size > 0) {
//                      var count = 0
//                      var i = 0
//                      while (i < grantResults.size) {
//                          if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                              showDialogNotPermission(this)
//                          } else {
//                              count++
//                          }
//                          i++
//                      }
//                      if (count == grantResults.size) goToNextScreen()
//                  }
//                  super.onRequestPermissionsResult(
//                      requestCode, permissions!!,
//                      grantResults!!
//                  )
//              }
//              else -> super.onRequestPermissionsResult(
//                  requestCode, permissions!!,
//                  grantResults!!
//              )
//          }
//      }

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