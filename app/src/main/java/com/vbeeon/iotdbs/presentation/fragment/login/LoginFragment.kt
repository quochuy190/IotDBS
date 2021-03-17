package com.vbeeon.iotdbs.presentation.fragment.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.activity.ScanDeviceActivity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class LoginFragment : BaseFragment() {
    val kUserAdmin = "Admin"
    val kPassAdmin = "Admin@2021"
    val kUserFloor1 = "FloorOne"
    val kPassFloor1 = "Floor@2021"
    val kUserFloor2 = "FloorTwo"
    val kPassFloor2 = "Floor@2021"
    lateinit var userViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    var isShow: Boolean = false;
    override fun initView() {
        icEye.setOnSafeClickListener {
            if (isShow) {
                isShow = !isShow
                Glide.with(this).load(R.drawable.ic_eye).into(icEye)
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                edtPass.setSelection(edtPass.length());
            } else {
                isShow = !isShow
                Glide.with(this).load(R.drawable.ic_eye_show).into(icEye)
                edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                edtPass.setSelection(edtPass.length());
            }
        }
        buttonLogin.setOnSafeClickListener {
            // (context as LoginActivity).openFragment(ConfigWifiFragment(), true)
            if (isValidate())
                userViewModel.exeLogin(
                    this, edtUserName.text.toString().trim().toLowerCase(),
                    edtPass.text.toString().trim().toLowerCase()
                )
//                if ((edtUserName.text.toString().toUpperCase().equals(kUserAdmin.toUpperCase()) && edtPass.text.toString().toUpperCase().equals(kPassAdmin.toUpperCase())) ||
//                    (edtUserName.text.toString().toUpperCase().equals(kUserFloor1.toUpperCase()) && edtPass.text.toString().toUpperCase().equals(kPassFloor1.toUpperCase())) ||
//                    (edtUserName.text.toString().toUpperCase().equals(kUserFloor2.toUpperCase()) && edtPass.text.toString().toUpperCase().equals(kPassFloor2.toUpperCase()))
//                ) {
//                    SharedPrefs.instance.put(ConstantCommon.IS_LOGIN, true)
//                    (context as LoginActivity).launchActivity<MainActivity> { }
//                    (context as LoginActivity).finish()
//                } else {
//                    edtUserName.requestFocus()
//                    edtUserName.setSelection(0)
//                    showDialogMessage(context, getString(R.string.error_login))
//                }

        }
        loginQRCode.setOnSafeClickListener {
            startActivityForResult(ScanDeviceActivity.newIntent(context), REQUEST_CODE_SCAN)
        }
    }

    private fun isValidate(): Boolean {
        if (edtUserName.text.length == 0) {
            showDialogMessage(context, getString(R.string.validate_Username))
            edtUserName.requestFocus()
            edtUserName.setSelection(0)
            return false
        }
        if (edtPass.text.length == 0) {
            showDialogMessage(context, getString(R.string.validate_Pass))
            edtPass.requestFocus()
            edtPass.setSelection(0)
            return false
        }
        return true
    }

    override fun initViewModel() {
        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun observable() {
        userViewModel.loginRes.observe(this, Observer {
            if (it != null && it.size > 0) {
                SharedPrefs.instance.put(ConstantCommon.IS_LOGIN, true)
                SharedPrefs.instance.put(ConstantCommon.KEY_SAVE_LOGIN_USER_LIST_DEVICE, "")
                if (it[0].name.equals("admin")) {
                    (context as LoginActivity).launchActivity<MainActivity> { }
                    (context as LoginActivity).finish()
                } else {
                    showMessage("login success" + it[0].name)
                }

            } else {
                edtUserName.requestFocus()
                edtUserName.setSelection(0)
                showDialogMessage(context, getString(R.string.error_login))
            }
        })
    }

    val REQUEST_CODE_SCAN = 1111
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SCAN && data != null) {
                val resultScan: String? = data.getStringExtra(ScanDeviceActivity.DATA)
                Timber.e("" + resultScan)
                if (resultScan != null && resultScan.length > 0) {
                    if (resultScan.indexOf("VBee@2021") > 2) {
                        SharedPrefs.instance.put(ConstantCommon.IS_LOGIN, true)
                        SharedPrefs.instance.put(ConstantCommon.KEY_SAVE_LOGIN_USER_LIST_DEVICE, resultScan)
                        (context as LoginActivity).launchActivity<MainActivity> { }
                        (context as LoginActivity).finish()
                    } else {
                        showDialogMessage(context, "Mã QR Code không hợp lệ")
                    }
                } else {
                    showDialogMessage(context, "Mã QR Code không hợp lệ")
                }
            }
        }
    }
}