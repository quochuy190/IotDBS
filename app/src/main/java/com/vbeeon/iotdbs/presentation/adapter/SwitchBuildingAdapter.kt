package com.vbeeon.iotdbs.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.databinding.ItemRoomBinding
import com.vbeeon.iotdbs.databinding.ItemSwitchBuildingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class SwitchBuildingAdapter internal constructor(val context: Context,
                                                 val doneClick: (Int) -> Unit) : RecyclerView.Adapter<SwitchBuildingAdapter.ViewHolder>(){
    private var listRoom = emptyList<Switch>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSwitchBuildingBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemSwitchBuildingBinding

        fun bind(entity: Switch?) {
            itemRoomBinding.data = entity
            val iv = ImageView(context)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)

            lp.setMargins(0, 11, 7, 0)
            iv.setLayoutParams(lp)
            iv.setImageResource(R.drawable.ic_switch_detail_off)
            iv.getLayoutParams().height = 90;
            iv.getLayoutParams().width = 96;

            itemRoomBinding.llItemSwitch.addView(iv); // lo agregamos al layout
            if (entity!!.listSubSw.size>0){

            }
            itemRoomBinding.executePendingBindings()
            if (entity!!.isChecked){
//                val sysTitle = "<u><b><font color='#026BBE'>"+entity.name+"</font></b></u>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
            }else{
//                val sysTitle = "<font color='#FF000000'>"+entity.name+"</font>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
            }
        }
        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSwitchBuildingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_switch_building, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRoom.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listRoom[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            doneClick(position)
        }
    }
    internal fun setDatas(list: List<Switch>) {
        this.listRoom = list
        notifyDataSetChanged()
    }

}