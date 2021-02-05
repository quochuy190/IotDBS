package com.vbeeon.iotdbs.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.databinding.ItemSwitchBuildingBinding
import com.vbeeon.iotdbs.databinding.ItemSwitchDetailBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class SwitchDetailAdapter internal constructor(val context: Context,
                                               val itemClick: (position: Int) -> Unit) : RecyclerView.Adapter<SwitchDetailAdapter.ViewHolder>(){
    private var listRoom = emptyList<SwitchDetailEntity>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSwitchDetailBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemSwitchBinding: ItemSwitchDetailBinding

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(entity: SwitchDetailEntity?) {
            itemSwitchBinding.data = entity
            itemSwitchBinding.executePendingBindings()
            if (entity!!.isChecked){
                itemSwitchBinding.imgSwitchDetail.setImageDrawable(context.getDrawable(R.drawable.ic_switch_detail_on))
            }else{
                itemSwitchBinding.imgSwitchDetail.setImageDrawable(context.getDrawable(R.drawable.ic_switch_detail_off))
//                val sysTitle = "<font color='#FF000000'>"+entity.name+"</font>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
            }
        }
        init {
            itemSwitchBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSwitchDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_switch_detail, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRoom.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listRoom[position])
//        holder.itemView.setOnClickListener() {
//            itemClick(position)
//            Timber.d("click item")
//
//        }
        holder.itemSwitchBinding.imgSwitchDetail.setOnSafeClickListener {
            itemClick(position)
        }
    }
    internal fun setDatas(list: List<SwitchDetailEntity>) {
        this.listRoom = list
        notifyDataSetChanged()
    }

}