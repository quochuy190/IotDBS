package com.vbeeon.iotdbs.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.view.CircularSeekBar
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*


@Suppress("DEPRECATION")
class DemoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_switch_detail_change_color
    }

    override fun initView() {
//        progress.progressChangedCallback = {
//            tvLableUtil.text = String.format("%.2f", it)
//        }
        mCircularSeekBar.setDrawMarkings(false)
        mCircularSeekBar.dotMarkers = true
        mCircularSeekBar.setRoundedEdges(true)
        mCircularSeekBar.setIsGradient(true)
        mCircularSeekBar.setPopup(false)
        mCircularSeekBar.sweepAngle = 270
        mCircularSeekBar.arcRotation = 225
        mCircularSeekBar.arcThickness = 70
        mCircularSeekBar.min = 800
        mCircularSeekBar.max = 20000
        mCircularSeekBar.progress = 0F
        mCircularSeekBar.setIncreaseCenterNeedle(20)
        mCircularSeekBar.valueStep = 10
        mCircularSeekBar.setNeedleFrequency(0.5f)
        mCircularSeekBar.needleDistanceFromCenter = 32
        mCircularSeekBar.setNeedleLengthInDP(15)
        mCircularSeekBar.setIncreaseCenterNeedle(24)
        mCircularSeekBar.needleThickness = 1.toFloat()
        mCircularSeekBar.setHeightForPopupFromThumb(5)

        // Setting textview with the seek bar value
       // mSeekBarValue.text = progressValue.toString() + "\u00B0"
        mCircularSeekBar.setOnCircularSeekBarChangeListener(object : CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(CircularSeekBar: CircularSeekBar, progress: Float, fromUser: Boolean) {
              //  mCircularSeekBar.setMinimumAndMaximumNeedleScale(progress - 2.5f, progress + 2.5f)
                // cal api
            }

            override fun onStartTrackingTouch(CircularSeekBar: CircularSeekBar) {

            }

            override fun onStopTrackingTouch(CircularSeekBar: CircularSeekBar) {

            }
        })

    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}