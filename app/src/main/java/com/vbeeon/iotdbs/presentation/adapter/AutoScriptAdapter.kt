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
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.model.ItemMenu
import com.vbeeon.iotdbs.databinding.ItemAutoScriptBinding
import com.vbeeon.iotdbs.databinding.ItemMenuBinding
import com.vbeeon.iotdbs.databinding.ItemScriptBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class AutoScriptAdapter internal constructor(val context: Context,
                                             val doneClick: (Int) -> Unit) : RecyclerView.Adapter<AutoScriptAdapter.ViewHolder>(){
    private var listScript = emptyList<ScriptEntity>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemAutoScriptBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemScriptBinding: ItemAutoScriptBinding

        fun bind(obj: ScriptEntity?) {
            itemScriptBinding.data = obj
            itemScriptBinding.executePendingBindings()

        }
        init {
            itemScriptBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAutoScriptBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_auto_script, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listScript.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScript[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            doneClick(position)
        }
    }
    internal fun setDatas(list: List<ScriptEntity>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}