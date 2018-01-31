package com.handstandsam.shoppingapp.models.builders

import com.handstandsam.shoppingapp.models.Item


class ItemBuilder(label: String) {

    internal var item: Item = Item(label, null, null)

    fun label(label: String): ItemBuilder {
        item.label = label
        return this
    }

    fun image(image: String): ItemBuilder {
        item.image = image
        return this
    }

    fun link(link: String): ItemBuilder {
        item.link = link
        return this
    }

    fun build(): Item {
        return item
    }

}
