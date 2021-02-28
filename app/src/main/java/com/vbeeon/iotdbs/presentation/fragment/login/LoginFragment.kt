package com.vbeeon.iotdbs.presentation.fragment.login

import android.os.Bundle
import android.text.InputType
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.MainActivity
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.activity.LoginActivity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class LoginFragment : BaseFragment() {
    val kUserAdmin = "Admin"
    val kPassAdmin = "Admin@2021"
    val kUserFloor1 = "FloorOne"
    val kPassFloor1 = "Floor@2021"
    val kUserFloor2 = "FloorTwo"
    val kPassFloor2 = "Floor@2021"
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }
    var isShow : Boolean = false;
    override fun initView() {
        icEye.setOnSafeClickListener {
            if (isShow){
                isShow = !isShow
                Glide.with(this).load(R.drawable.ic_eye).into(icEye)
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                edtPass.setSelection(edtPass.length());
            }else{
                isShow = !isShow
                Glide.with(this).load(R.drawable.ic_eye_show).into(icEye)
                edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                edtPass.setSelection(edtPass.length());
            }
        }
        buttonLogin.setOnSafeClickListener {
            // (context as LoginActivity).openFragment(ConfigWifiFragment(), true)
            if (isValidate())
                if ((edtUserName.text.toString().toUpperCase().equals(kUserAdmin.toUpperCase()) && edtPass.text.toString().toUpperCase().equals(kPassAdmin.toUpperCase())) ||
                    (edtUserName.text.toString().toUpperCase().equals(kUserFloor1.toUpperCase()) && edtPass.text.toString().toUpperCase().equals(kPassFloor1.toUpperCase())) ||
                    (edtUserName.text.toString().toUpperCase().equals(kUserFloor2.toUpperCase()) && edtPass.text.toString().toUpperCase().equals(kPassFloor2.toUpperCase()))
                ) {
                    SharedPrefs.instance.put(ConstantCommon.IS_LOGIN, true)
                    (context as LoginActivity).launchActivity<MainActivity> { }
                    (context as LoginActivity).finish()
                } else {
                    edtUserName.requestFocus()
                    edtUserName.setSelection(0)
                    showDialogMessage(context, getString(R.string.error_login))
                }

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

    }

    override fun observable() {

    }


}