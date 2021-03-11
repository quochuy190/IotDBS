package com.vbeeon.iotdbs.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.model.Floor
import com.vbeeon.iotdbs.databinding.ItemFloorChoseBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class FloorChoseAdapter internal constructor(val context: Context,
                                             val doneClick: (Int) -> Unit) : RecyclerView.Adapter<FloorChoseAdapter.ViewHolder>(){
    private var listRoom = emptyList<Floor>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemFloorChoseBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemFloorChoseBinding
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(entity: Floor?) {
            itemRoomBinding.data = entity
            var adapterSubSWAdapter : RoomChoseAdapter
            adapterSubSWAdapter = RoomChoseAdapter(context, doneClick = {

            })
            itemRoomBinding.rcvRoom.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
            itemRoomBinding.rcvRoom.apply { adapter = adapterSubSWAdapter }
            itemRoomBinding.rcvRoom.isNestedScrollingEnabled = false
            itemRoomBinding.executePendingBindings()
            adapterSubSWAdapter.setDatas(entity!!.listRoom)
        }
        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFloorChoseBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_floor_chose, parent, false)
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
    internal fun setDatas(list: List<Floor>) {
        this.listRoom = list
        notifyDataSetChanged()
    }
    internal fun updateDatas(list: List<Floor>) {
        this.listRoom = list
        notifyDataSetChanged()
    }

}