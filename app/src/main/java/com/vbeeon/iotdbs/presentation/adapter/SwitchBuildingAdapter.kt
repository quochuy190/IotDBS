package com.vbeeon.iotdbs.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.databinding.ItemSwitchBuildingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class SwitchBuildingAdapter internal constructor(val context: Context,
                                                 val doneClick: (Int) -> Unit) : RecyclerView.Adapter<SwitchBuildingAdapter.ViewHolder>(){
    private var listRoom = emptyList<Switch>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSwitchBuildingBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemSwitchBuildingBinding

        fun bind(entity: Switch?) {
            itemRoomBinding.data = entity
            if (entity!!.listSubSw.size>0){
                when (entity!!.listSubSw.size){
                    1 -> {
                        itemRoomBinding.imgSW1.visibility = View.VISIBLE
                        itemRoomBinding.imgSW2.visibility = View.GONE
                        itemRoomBinding.imgSW3.visibility = View.GONE
                    }
                    2 -> {
                        itemRoomBinding.imgSW1.visibility = View.VISIBLE
                        itemRoomBinding.imgSW2.visibility = View.VISIBLE
                        itemRoomBinding.imgSW3.visibility = View.GONE
                    }
                    3 -> {
                        itemRoomBinding.imgSW1.visibility = View.VISIBLE
                        itemRoomBinding.imgSW2.visibility = View.VISIBLE
                        itemRoomBinding.imgSW3.visibility = View.VISIBLE
                    }
                }
            }else{
                itemRoomBinding.imgSW1.visibility = View.GONE
                itemRoomBinding.imgSW2.visibility = View.GONE
                itemRoomBinding.imgSW3.visibility = View.GONE
            }
            itemRoomBinding.executePendingBindings()
//            if (entity!!.isChecked){
////                val sysTitle = "<u><b><font color='#026BBE'>"+entity.name+"</font></b></u>"
////                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
//            }else{
////                val sysTitle = "<font color='#FF000000'>"+entity.name+"</font>"
////                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
//            }
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
    internal fun updateDatas(list: List<Switch>) {
        this.listRoom = list
        notifyDataSetChanged()
    }

}