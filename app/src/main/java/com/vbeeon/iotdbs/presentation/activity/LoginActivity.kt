package com.vbeeon.iotdbs.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.login.IntroduceFragment
import com.vbeeon.iotdbs.presentation.fragment.login.LoginFragment
import com.vbeeon.iotdbs.utils.SharedPrefs
import com.vbeeon.iotdbs.utils.openFragment
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class LoginActivity : AppCompatActivity() {
    var PERMISSION_CAMERA : Int = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission(Manifest.permission.CAMERA)
        val isFirs =
            SharedPrefs.instance.get(ConstantCommon.IS_FIRST_OPEN_APP, Boolean::class.java)
        if (!isFirs)
            openFragment(IntroduceFragment(), false)
        else
            openFragment(LoginFragment(), false)
    }

    fun requestPermission(permission: String): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(this!!, permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                PERMISSION_CAMERA
            )
        }
        return isGranted
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted so open camera or gallery based on you click

            }
        }
    }
}