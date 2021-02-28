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
import com.bumptech.glide.Glide
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.model.ItemMenu
import com.vbeeon.iotdbs.databinding.ItemMenuBinding
import com.vbeeon.iotdbs.databinding.ItemScriptBinding
import com.vbeeon.iotdbs.databinding.ItemSubSwBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import kotlinx.android.synthetic.main.item_sub_sw.view.*
import timber.log.Timber


class SubSWAdapter internal constructor(val context: Context,
                                        val doneClick: (Int) -> Unit) : RecyclerView.Adapter<SubSWAdapter.ViewHolder>(){
    private var listMenu = emptyList<SwitchDetailEntity>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSubSwBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemMenuBinding: ItemSubSwBinding
        fun bind(obj: SwitchDetailEntity?) {
            itemMenuBinding.data = obj
            itemMenuBinding.executePendingBindings()
        }
        init {
            itemMenuBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSubSwBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_sub_sw, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listMenu.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenu[position])
        holder.itemMenuBinding.cbSubSW.setOnCheckedChangeListener { buttonView, isChecked ->
            listMenu[position].isChecked = isChecked
            notifyDataSetChanged()
        }
    }
    internal fun setDatas(list: List<SwitchDetailEntity>) {
        this.listMenu = list
        notifyDataSetChanged()
    }

}