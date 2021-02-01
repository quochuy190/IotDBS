package com.vbeeon.iotdbs.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.databinding.ItemRoomBinding
import com.vbeeon.iotdbs.databinding.ItemSwitchBuildingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class SwitchBuildingAdapter internal constructor(context: Context,
                                                 val doneClick: (Int) -> Unit) : RecyclerView.Adapter<SwitchBuildingAdapter.ViewHolder>(){
    private var listRoom = emptyList<SwitchEntity>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSwitchBuildingBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemSwitchBuildingBinding

        fun bind(entity: SwitchEntity?) {
            itemRoomBinding.data = entity
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
    internal fun setDatas(list: List<SwitchEntity>) {
        this.listRoom = list
        notifyDataSetChanged()
    }

}