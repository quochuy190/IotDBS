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
import com.vbeeon.iotdbs.databinding.ItemRoomBinding
import com.vbeeon.iotdbs.presentation.adapter.RoomBuildAdapter.ViewHolder
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class RoomBuildAdapter internal constructor(context: Context,
                                            val doneClick: (Int) -> Unit) : RecyclerView.Adapter<ViewHolder>(){
    private var listRoom = emptyList<RoomEntity>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemRoomBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemRoomBinding

        fun bind(roomEntity: RoomEntity?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            if (roomEntity!!.isSelected){
                val sysTitle = "<u><b><font color='#026BBE'>"+roomEntity.name+"</font></b></u>"
                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
               // itemRoomBinding.tvTimeCalName.setTextColor(Color.parseColor("#026BBE"));
//                itemRoomBinding.tvTimeCalName.typeface = Typeface.DEFAULT_BOLD
            }else{
                val sysTitle = "<font color='#FF000000'><b>"+roomEntity.name+"</b></font>"
                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
//                itemRoomBinding.tvTimeCalName.setTextColor(Color.parseColor("#FF000000"));
//                itemRoomBinding.tvTimeCalName.typeface = Typeface.DEFAULT
            }
        }
        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRoomBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_room, parent, false)
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
    internal fun setDatas(list: List<RoomEntity>) {
        this.listRoom = list
        notifyDataSetChanged()
    }

}