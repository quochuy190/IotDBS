package com.vbeeon.iotdbs.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vbeeon.iotdbs.BR;
import com.vbeeon.iotdbs.R;
import com.vbeeon.iotdbs.data.local.entity.RoomEntity;
import com.vbeeon.iotdbs.databinding.ItemRoomBinding;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-January-2021
 * Time: 23:42
 * Version: 1.0
 */
class RoomAdapter extends ListAdapter<RoomEntity, RoomAdapter.ViewHolder> {

    public static final DiffUtil.ItemCallback<RoomEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<RoomEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull RoomEntity oldItem, @NonNull RoomEntity newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull RoomEntity oldItem, @NonNull RoomEntity newItem) {
                    return oldItem.isSelected() == newItem.isSelected();
                }
            };

    private Context mContext;

    public RoomAdapter(Context context) {
        super(DIFF_CALLBACK);
        mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoomBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_room, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomEntity tempLockSource = getItem(holder.getAdapterPosition());
        holder.bind(tempLockSource);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemRoomBinding itemRoomBinding;

        public ViewHolder(ItemRoomBinding itemLockAppBinding) {
            super(itemLockAppBinding.getRoot());
            this.itemRoomBinding = itemLockAppBinding;
        }

        public void bind(RoomEntity roomEntity) {
            itemRoomBinding.setVariable(BR.data, roomEntity);
            itemRoomBinding.executePendingBindings();
        }
    }

}
