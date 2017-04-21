package com.handstandsam.maintainableespresso.features.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.models.Item;

import java.util.ArrayList;
import java.util.List;

class CategoryRVAdapter extends RecyclerView.Adapter<ItemRowViewHolder> {

    List<Item> items = new ArrayList<>();

    public CategoryRVAdapter() {
    }

    @Override
    public ItemRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_item_row, parent, false);
        return new ItemRowViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ItemRowViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
