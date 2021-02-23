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
import com.vbeeon.iotdbs.databinding.ItemSwitchListChoseBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_building.*
import timber.log.Timber


class SwitchListChoseAdapter internal constructor(val context: Context,
                                                  val doneClick: (Int) -> Unit) : RecyclerView.Adapter<SwitchListChoseAdapter.ViewHolder>(){
    private var listRoom = emptyList<Switch>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSwitchListChoseBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemSwitchListChoseBinding
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(entity: Switch?) {
            itemRoomBinding.data = entity
            var adapterSubSWAdapter : SubSWAdapter
            adapterSubSWAdapter = SubSWAdapter(context, doneClick = {

            })
            itemRoomBinding.rcvListSWChose.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
            itemRoomBinding.rcvListSWChose.apply { adapter = adapterSubSWAdapter }
            itemRoomBinding.executePendingBindings()
            adapterSubSWAdapter.setDatas(entity!!.listSubSw)
        }
        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSwitchListChoseBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_switch_list_chose, parent, false)
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