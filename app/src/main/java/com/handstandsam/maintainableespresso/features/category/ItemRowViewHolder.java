package com.handstandsam.maintainableespresso.features.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.features.itemdetail.ItemDetailActivity;
import com.handstandsam.maintainableespresso.features.itemdetail.ItemDetailPresenter;
import com.handstandsam.maintainableespresso.models.Item;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


class ItemRowViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.image)
    AppCompatImageView imageView;

    private Item item;

    public ItemRowViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = itemView.getContext();
                Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(ItemDetailPresenter.BUNDLE_PARAM_ITEM, item);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    private static final int colors[] = {R.color.material_amber, R.color.material_blue,
            R.color.material_blue_grey, R.color.material_pink,
            R.color.material_cyan, R.color.material_deep_orange, R.color.material_deep_purple,
            R.color.material_green, R.color.material_blue, R.color.material_light_blue,
            R.color.material_brown, R.color.material_amber};

    public void bindData(Item item, int position) {
        int colorIdx = position % colors.length;
        Timber.d("idx: " + colorIdx);
        int colorResource = colors[colorIdx];
        itemView.setBackgroundResource(colorResource);
        this.item = item;

        Glide.with(imageView.getContext()).load(item.getImage()).into(imageView);
        textView.setText(this.item.getLabel());
    }
}
