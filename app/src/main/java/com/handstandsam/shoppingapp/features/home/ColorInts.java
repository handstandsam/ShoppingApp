package com.handstandsam.shoppingapp.features.home;

import android.support.annotation.ColorInt;

import com.handstandsam.shoppingapp.R;

import timber.log.Timber;


public class ColorInts {


    public static final int colors[] = {R.color.material_amber, R.color.material_blue,
            R.color.material_blue_grey, R.color.material_pink,
            R.color.material_cyan, R.color.material_light_blue,
            R.color.material_deep_orange, R.color.material_deep_purple,
            R.color.material_green, R.color.material_blue,
            R.color.material_brown, R.color.material_amber};

    public static int getColor(int position) {
        int colorIdx = position % colors.length;
        Timber.d("idx: " + colorIdx);
        int colorResource = ColorInts.colors[colorIdx];
        return colorResource;
    }
}
