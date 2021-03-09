package com.vbeeon.iotdbs.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.databinding.ItemSwitchBuildingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class SwitchBuildingAdapter internal constructor(
    val context: Context,
    val doneClick: (Int) -> Unit
) : RecyclerView.Adapter<SwitchBuildingAdapter.ViewHolder>() {
    private var listRoom = emptyList<Switch>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSwitchBuildingBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemSwitchBuildingBinding

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(entity: Switch?) {
            itemRoomBinding.data = entity
            if (entity!!.typeView == 1) {
                itemRoomBinding.tvFloor.visibility = View.VISIBLE
                itemRoomBinding.tvRoomName.visibility = View.VISIBLE
            } else if (entity!!.typeView == 2) {
                itemRoomBinding.tvFloor.visibility = View.GONE
                itemRoomBinding.tvRoomName.visibility = View.GONE
            }
            if (entity!!.listSubSw.size > 0) {
                if (entity!!.type == 5) {
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.icon_sw_dimming))
                        .into(itemRoomBinding.imgSwitchCover);
                    itemRoomBinding.imgSW1.visibility = View.VISIBLE
                    itemRoomBinding.imgSW2.visibility = View.GONE
                    itemRoomBinding.imgSW3.visibility = View.GONE
                    if (entity.listSubSw[0]!!.isChecked) {
                        Glide.with(context)
                            .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                            .into(itemRoomBinding.imgSW1);
                    } else
                        Glide.with(context)
                            .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                            .into(itemRoomBinding.imgSW1);
                } else
                    when (entity!!.listSubSw.size) {
                        1 -> {
                            Glide.with(context)
                                .load(context.getDrawable(R.drawable.switch_one))
                                .into(itemRoomBinding.imgSwitchCover);
                            itemRoomBinding.imgSW1.visibility = View.VISIBLE
                            itemRoomBinding.imgSW2.visibility = View.GONE
                            itemRoomBinding.imgSW3.visibility = View.GONE
                            if (entity!!.listSubSw[0].isChecked) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                                    .into(itemRoomBinding.imgSW1);
                            } else {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                                    .into(itemRoomBinding.imgSW1);
                            }
                        }
                        2 -> {
                            Glide.with(context)
                                .load(context.getDrawable(R.drawable.switch_two))
                                .into(itemRoomBinding.imgSwitchCover);
                            itemRoomBinding.imgSW1.visibility = View.VISIBLE
                            itemRoomBinding.imgSW2.visibility = View.VISIBLE
                            itemRoomBinding.imgSW3.visibility = View.GONE
                            if (entity!!.listSubSw[0].isChecked) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                                    .into(itemRoomBinding.imgSW1);
                            } else {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                                    .into(itemRoomBinding.imgSW1);
                            }
                            if (entity!!.listSubSw[1].isChecked) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                                    .into(itemRoomBinding.imgSW2);
                            } else {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                                    .into(itemRoomBinding.imgSW2);
                            }
                        }
                        3 -> {
                            Glide.with(context)
                                .load(context.getDrawable(R.drawable.switch_three))
                                .into(itemRoomBinding.imgSwitchCover);
                            itemRoomBinding.imgSW1.visibility = View.VISIBLE
                            itemRoomBinding.imgSW2.visibility = View.VISIBLE
                            itemRoomBinding.imgSW3.visibility = View.VISIBLE
                            if (entity!!.listSubSw[0].isChecked) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                                    .into(itemRoomBinding.imgSW1);
                            } else {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                                    .into(itemRoomBinding.imgSW1);
                            }
                            if (entity!!.listSubSw[1].isChecked) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                                    .into(itemRoomBinding.imgSW2);
                            } else {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                                    .into(itemRoomBinding.imgSW2);
                            }
                            if (entity!!.listSubSw[2].isChecked) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detail_on))
                                    .into(itemRoomBinding.imgSW3);
                            } else {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.ic_switch_detal_off))
                                    .into(itemRoomBinding.imgSW3);
                            }
                        }
                    }
            } else {
                if (entity!!.type == 4) {
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.ic_motion_sensor))
                        .into(itemRoomBinding.imgSwitchCover);
                    itemRoomBinding.imgSW1.visibility = View.GONE
                    itemRoomBinding.imgSW2.visibility = View.GONE
                    itemRoomBinding.imgSW3.visibility = View.GONE
                }
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
        val binding: ItemSwitchBuildingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_switch_building, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRoom.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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