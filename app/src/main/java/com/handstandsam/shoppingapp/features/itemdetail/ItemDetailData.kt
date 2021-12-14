package com.handstandsam.shoppingapp.features.itemdetail

import com.handstandsam.shoppingapp.models.Item
import java.io.Serializable

data class ItemDetailData(
    val label: String,
    val image: String,
    val link: String? = null
) : Serializable {

    fun toItem(): Item {
        return Item(
            label = label,
            image = image,
            link = link
        )
    }

    companion object {
        fun fromItem(item: Item): ItemDetailData {
            return ItemDetailData(
                label = item.label,
                image = item.image,
                link = item.link
            )
        }
    }
}