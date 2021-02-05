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
import com.vbeeon.iotdbs.databinding.ItemScriptBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.setTextHTML
import timber.log.Timber


class ScriptAdapter internal constructor(context: Context,
                                         val doneClick: (Int) -> Unit) : RecyclerView.Adapter<ScriptAdapter.ViewHolder>(){
    private var listScript = emptyList<ScriptEntity>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemScriptBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemScriptBinding

        fun bind(roomEntity: ScriptEntity?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
//            if (roomEntity!!.isSelected){
//                val sysTitle = "<b><font color='#000000'>"+roomEntity.name+"</font></b>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
//               // itemRoomBinding.tvTimeCalName.setTextColor(Color.parseColor("#026BBE"));
////                itemRoomBinding.tvTimeCalName.typeface = Typeface.DEFAULT_BOLD
//            }else{
//                val sysTitle = "<font color='#cccccc'>"+roomEntity.name+"</font>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
////                itemRoomBinding.tvTimeCalName.setTextColor(Color.parseColor("#FF000000"));
////                itemRoomBinding.tvTimeCalName.typeface = Typeface.DEFAULT
//            }
        }
        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemScriptBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_room, parent, false)
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