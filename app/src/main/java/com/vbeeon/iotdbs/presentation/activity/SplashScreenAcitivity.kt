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
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Glide.with(this).load(R.drawable.bg_login).into(backGround)
        goToNextScreen()
        initViewModel()
    }
    var start : Boolean= false
    private fun initViewModel() {
    }
    private fun goToNextScreen() {
        Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val isFirs =
                SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
            val isLogin =
                SharedPrefs.instance.get(ConstantCommon.IS_LOGIN, Boolean::class.java)
            val isUpdate =
                SharedPrefs.instance.get(ConstantCommon.IS_UPDATE_1_5, Boolean::class.java)
            if (!isFirs||!isLogin||!isUpdate) {
                this.launchActivity<LoginActivity>()
            } else {
                this.launchActivity<MainActivity>()
            }
            finish()
        }, 1000)
    }

}