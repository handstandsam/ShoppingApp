package com.handstandsam.maintainableespresso.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handstandsam.maintainableespresso.R;

class HomeRVAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    String[] data = {"Fruits", "Vegetables", "Dairy", "Grain", "Meats", "Desserts"};

    public HomeRVAdapter() {
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_row, parent, false);
        return new HomeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.bindData(data[position], position);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
