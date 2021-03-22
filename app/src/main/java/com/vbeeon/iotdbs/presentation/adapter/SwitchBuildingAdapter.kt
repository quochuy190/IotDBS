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
import androidx.recyclerview.widget.LinearLayoutManager
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
            if (entity!!.listSubSw!=null&&entity!!.listSubSw.size > 0) {
                var adapterSubSWAdapter : SwStateAdapter
                adapterSubSWAdapter = SwStateAdapter(context, doneClick = {

                })
                itemRoomBinding.rcvSWState.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
                itemRoomBinding.rcvSWState.apply { adapter = adapterSubSWAdapter }
                itemRoomBinding.executePendingBindings()
                adapterSubSWAdapter.setDatas(entity!!.listSubSw)

            } else {
                if (entity!!.type == 4) {
                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.ic_motion_sensor))
                        .into(itemRoomBinding.imgSwitchCover);
                }
            }

            itemRoomBinding.executePendingBindings()
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