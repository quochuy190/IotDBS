package com.vbeeon.iotdbs.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.fragment.MainFragment
import com.vbeeon.iotdbs.presentation.fragment.login.LoginFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splashscreen.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(LoginFragment(), false)
    }
}