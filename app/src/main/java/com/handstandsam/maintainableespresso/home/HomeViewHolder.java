package com.handstandsam.maintainableespresso.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.category.CategoryActivity;
import com.handstandsam.maintainableespresso.category.CategoryPresenter;
import com.handstandsam.maintainableespresso.models.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


class HomeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text)
    TextView textView;

    Category category;

    public HomeViewHolder(final View itemView) {
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

    private static final int colors[] = {R.color.material_amber, R.color.material_blue,
            R.color.material_blue_grey, R.color.material_pink,
            R.color.material_cyan, R.color.material_light_blue,
            R.color.material_deep_orange, R.color.material_deep_purple,
            R.color.material_green, R.color.material_blue,
            R.color.material_brown, R.color.material_amber};

    public void bindData(Category category, int position) {
        int colorIdx = position % colors.length;
        Timber.d("idx: " + colorIdx);
        int colorResource = colors[colorIdx];
        itemView.setBackgroundResource(colorResource);
        this.category = category;
        textView.setText(this.category.getLabel());
    }
}
