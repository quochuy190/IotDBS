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
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.UserEntity
import com.vbeeon.iotdbs.data.model.Week
import com.vbeeon.iotdbs.databinding.ItemScriptBinding
import com.vbeeon.iotdbs.databinding.ItemUserBinding
import com.vbeeon.iotdbs.databinding.ItemWeekBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class WeekAdapter internal constructor(var context: Context,
                                       val doneClick: (Int) -> Unit) :
    RecyclerView.Adapter<WeekAdapter
.ViewHolder>(){
    private var listScript = emptyList<Week>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemWeekBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemWeekBinding

        fun bind(roomEntity: Week?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            if (roomEntity!!.isChecked){
                itemRoomBinding.llItemWeek.background = context.getDrawable(R.color.textAppBar)
            }else{
                itemRoomBinding.llItemWeek.background = context.getDrawable(R.color.grey_20)
            }
        }
        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWeekBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_week, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listScript.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScript[position])
        holder.itemView.setOnSafeClickListener {
            //doneClick(position)
            listScript[position].isChecked = !listScript[position].isChecked
            notifyDataSetChanged()
        }

    }
    internal fun setDatas(list: List<Week>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}