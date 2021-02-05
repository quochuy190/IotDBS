package com.vbeeon.iotdbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vbeeon.iotdbs.presentation.fragment.DemoFragment
import com.vbeeon.iotdbs.presentation.fragment.MainFragment
import com.vbeeon.iotdbs.utils.openFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(DemoFragment(), false)
    }
}