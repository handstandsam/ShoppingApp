package com.handstandsam.shoppingapp.features.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.features.category.CategoryActivity;
import com.handstandsam.shoppingapp.features.category.CategoryPresenter;
import com.handstandsam.shoppingapp.models.Category;

import butterknife.BindView;
import butterknife.ButterKnife;


class CategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.image)
    ImageView imageView;

    Category category;

    public CategoryViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, CategoryActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(CategoryPresenter.BUNDLE_PARAM_CATEGORY, category);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    public void bindData(Category category, int position) {
        this.category = category;

        String imageUrl = category.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(imageView.getContext()).load(category.getImage()).into(imageView);
        } else {
            itemView.setBackgroundResource(ColorInts.getColor(position));
        }

        textView.setText(this.category.getLabel());
    }
}
