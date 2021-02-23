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
import com.vbeeon.iotdbs.data.model.ItemMenu
import com.vbeeon.iotdbs.databinding.ItemMenuBinding
import com.vbeeon.iotdbs.databinding.ItemScriptBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class ItemMenuAdapter internal constructor(val context: Context,
                                           val doneClick: (Int) -> Unit) : RecyclerView.Adapter<ItemMenuAdapter.ViewHolder>(){
    private var listMenu = emptyList<ItemMenu>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemMenuBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemMenuBinding: ItemMenuBinding

        fun bind(obj: ItemMenu?) {
            itemMenuBinding.data = obj
            itemMenuBinding.executePendingBindings()
            if (obj!!.res>0){
                Glide.with(context)
                        .load(obj.res)
                        .into(itemMenuBinding.imgItemMenu);
            }
        }
        init {
            itemMenuBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMenuBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_menu, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listMenu.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenu[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            doneClick(position)
        }
    }
    internal fun setDatas(list: List<ItemMenu>) {
        this.listMenu = list
        notifyDataSetChanged()
    }

}