package com.vbeeon.iotdbs.presentation.fragment.switchDetail

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.model.Week
import com.vbeeon.iotdbs.presentation.adapter.WeekAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*
import kotlinx.android.synthetic.main.fragment_timer.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class TimerFragment : BaseFragment() {
    var mHours : Int =-1
    var mMinute : Int = -1
    lateinit var mainViewModel: MainViewModel
    val mListWeek: MutableList<Week> = ArrayList()
    lateinit var adapterWeek : WeekAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    companion object {
        fun newInstance(group: String, floor: Int): TimerFragment {
            val fragment = TimerFragment()
            val args = Bundle()
            args.putString("group_name", group)
            args.putInt("floor", floor)
            fragment.setArguments(args)
            return fragment
        }
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_timer
    }

    override fun initView() {
        mListWeek.add(Week(0, "T2", false, 0, "MON"))
        mListWeek.add(Week(1, "T3", false, 0, "TUE"))
        mListWeek.add(Week(2, "T4", false, 0, "WED"))
        mListWeek.add(Week(3, "T5", false, 0, "THU"))
        mListWeek.add(Week(4, "T6", false, 0, "FRI"))
        mListWeek.add(Week(5, "T7", false, 0, "SAT"))
        mListWeek.add(Week(6, "CN", false, 0, "SUN"))
        adapterWeek = context?.let {
            WeekAdapter(it, doneClick = {

            })
        }!!
        rcvWeek.layoutManager = GridLayoutManager(context, 7)
        rcvWeek.apply { adapter = adapterWeek }
        adapterWeek.setDatas(mListWeek)
        edtTimePicker.setOnSafeClickListener {
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                context,
                { timePicker, selectedHour, selectedMinute -> edtTimePicker.setText("$selectedHour:$selectedMinute")
                    mHours = selectedHour
                    mMinute = selectedMinute
                }, hour, minute, true
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }
        btnSaveTimer.setOnSafeClickListener {
            if (mHours>-1&&mMinute>-1){
                var sWeekString = ""
                var jsonWeek = ""
                for (week in mListWeek){
                    if (week.isChecked){
                        sWeekString = sWeekString+","+week.description
                    }
                }
                if (sWeekString.length==0){
                    showDialogMessage(context, "Bạn chưa chọn ngày trong tuần!")
                }else{
                    jsonWeek = sWeekString.substring(1, sWeekString.length)
                    when (sWeekString){

                        ",MON,TUE,WED" ->{
                            jsonWeek = "MON-WED"
                        }
                        ",MON,TUE,WED,THU" ->{
                            jsonWeek = "MON-WED"
                        }
                        ",MON,TUE,WED,THU,FRI" ->{
                            jsonWeek = "MON-FRI"
                        }
                        ",MON,TUE,WED,THU,FRI,SAT" ->{
                            jsonWeek = "MON-SAT"
                        }
                        ",MON,TUE,WED,THU,FRI,SAT,SUN" ->{
                            jsonWeek = "MON-SUN"
                        }
                    }
                }
               Timber.d(jsonWeek)
            }else{
                showDialogMessage(context, "Bạn chưa nhập vào thời gian phù hợp.")
            }
        }
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}