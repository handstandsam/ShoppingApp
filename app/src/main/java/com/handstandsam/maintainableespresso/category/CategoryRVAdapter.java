package com.handstandsam.maintainableespresso.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.models.Item;

import java.util.ArrayList;
import java.util.List;

class CategoryRVAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    List<Item> items = new ArrayList<>();

    public CategoryRVAdapter() {
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_category_row, parent, false);
        return new CategoryViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
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
