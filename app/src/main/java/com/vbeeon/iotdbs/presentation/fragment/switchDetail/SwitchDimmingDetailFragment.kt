package com.vbeeon.iotdbs.presentation.fragment.switchDetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.view.CircularSeekBar
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_switch_detail.*
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.tvLocationRoom
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConfigNetwork


@Suppress("DEPRECATION")
class SwitchDimmingDetailFragment : BaseFragment() {
    val mListSwitch: MutableList<SwitchDetailEntity> = ArrayList()
    val mListSubSWString: MutableList<String> = ArrayList()
    var switchId: String = ""
    var switchName: String = ""
    lateinit var mainViewModel: MainViewModel
    var isState: Boolean = false;
    var mSwDim = 0;
    var mSwColor = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_switch_detail_change_color
    }

    companion object {
        fun newInstance(id: String, name: String, floor: Int): SwitchDimmingDetailFragment {
            val fragment = SwitchDimmingDetailFragment()
            val args = Bundle()
            args.putString("switch_id", id)
            args.putString("switch_name", name)
            args.putInt("floor", floor)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("switch_id")?.let {
            switchId = it
        }
        arguments?.getString("switch_name")?.let {
            switchName = it
        }
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = ""
        imgBack.visibility = View.VISIBLE
        tvLocationRoom.text = switchName
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
                mSwColor = progress.toInt()
                if (isState){
                    mainViewModel.exeControlSwDimming(1,switchId+"/sw2/control", mSwDim, mSwColor )
                }
            }

            override fun onStartTrackingTouch(CircularSeekBar: CircularSeekBar) {

            }

            override fun onStopTrackingTouch(CircularSeekBar: CircularSeekBar) {

            }
        })
        initEvent();
    }

    private fun initEvent() {
        imgSwitchDetalDimming.setOnSafeClickListener {
            var state: Int
            if (isState) {
                state = 0
            } else {
                state = 1
            }
            mainViewModel.exeControlSwDimming(state,switchId+"/sw2/control", mSwDim, mSwColor )
        }
        sekbarDimming.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO Auto-generated method stub
                tvSekbarDimming.text = "" + progress + " %"
                mSwDim = progress
                if (isState){
                    mainViewModel.exeControlSwDimming(1,switchId+"/sw2/control", mSwDim, mSwColor )
                }
            }
        })
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loadDataSubSwitch(this, switchId)
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable ->
//            showDialogMessage(
//                context,
//                throwable.message
//            )
        })
        //   mainViewModel.exeCreateGroupRemoteBySW(switchId)

    }

    @SuppressLint("NewApi")
    override fun observable() {
        mainViewModel.subSwRespon.observe(this, Observer {
            //create switch
            if (it != null && it.size > 0) {
                mListSwitch.addAll(it)
                if (it[0].isChecked) {
                    isState = true
                    imgSwitchDetalDimming.setImageDrawable(activity?.getDrawable(R.drawable.ic_switch_detail_on))
                } else {
                    isState = false
                    imgSwitchDetalDimming.setImageDrawable(activity?.getDrawable(R.drawable.ic_switch_detal_off))
                }
            }
        })
        mainViewModel.resControlSubSW.observe(this, Observer {
            if (it == 1) {
                mListSwitch[0].isChecked = true
                isState = true
                imgSwitchDetalDimming.setImageDrawable(activity?.getDrawable(R.drawable.ic_switch_detail_on))
            } else {
                mListSwitch[0].isChecked = false
                isState = false
                imgSwitchDetalDimming.setImageDrawable(activity?.getDrawable(R.drawable.ic_switch_detal_off))
            }
            mainViewModel.updateListSubSW(mListSwitch)
        })
    }


}