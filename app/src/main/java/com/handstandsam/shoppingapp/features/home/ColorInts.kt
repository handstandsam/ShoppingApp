package com.handstandsam.shoppingapp.features.home

import com.handstandsam.shoppingapp.R

import timber.log.Timber


object ColorInts {

    val colors = intArrayOf(
        R.color.material_amber,
        R.color.material_blue,
        R.color.material_blue_grey,
        R.color.material_pink,
        R.color.material_cyan,
        R.color.material_light_blue,
        R.color.material_deep_orange,
        R.color.material_deep_purple,
        R.color.material_green,
        R.color.material_blue,
        R.color.material_brown,
        R.color.material_amber
    )

    @JvmStatic
    fun getColor(position: Int): Int {
        val colorIdx = position % colors.size
        Timber.d("idx: " + colorIdx)
        val colorResource = ColorInts.colors[colorIdx]
        return colorResource
    }
}
