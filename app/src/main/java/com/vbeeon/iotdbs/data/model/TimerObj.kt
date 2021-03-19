package com.vbeeon.iotdbs.data.model

data class TimerObj(val rn: String,val lbl: List<String>, val se: TimerControl, val sub : List<TimerState>)//0 off, 1// on