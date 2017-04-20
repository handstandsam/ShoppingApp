package com.handstandsam.maintainableespresso.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.handstandsam.maintainableespresso.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


class CategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text)
    TextView textView;

    private String data;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final int colors[] = {R.color.material_amber, R.color.material_blue,
            R.color.material_blue_grey, R.color.material_pink,
            R.color.material_cyan, R.color.material_deep_orange, R.color.material_deep_purple,
            R.color.material_green, R.color.material_blue, R.color.material_light_blue,
            R.color.material_brown, R.color.material_amber};

    public void bindData(String data, int position) {
        int colorIdx = position % colors.length;
        Timber.d("idx: " + colorIdx);
        int colorResource = colors[colorIdx];
        itemView.setBackgroundResource(colorResource);
        this.data = data;
        textView.setText(data);
    }
}
