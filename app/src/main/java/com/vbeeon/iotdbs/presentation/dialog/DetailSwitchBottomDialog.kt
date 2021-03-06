package com.vbeeon.iotdbs.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter
import com.vbeeon.iotdbs.presentation.adapter.RoomDialogAdapter
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.botton_sheet_dialog_select_room.*
import kotlinx.android.synthetic.main.fragment_building.*

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-February-2021
 * Time: 23:20
 * Version: 1.0
 */

public class DetailSwitchBottomDialog internal constructor(val idR: Int, val doneClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.switch_detail_botton_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadAllData(this)
        mainViewModel.devicesRes.observe(this, Observer {

        })
    }
}