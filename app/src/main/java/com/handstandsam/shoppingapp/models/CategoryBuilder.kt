package com.handstandsam.shoppingapp.models


class CategoryBuilder() {

    internal var category: Category = Category(null, null, null)

    constructor(label: String) : this() {
        label(label)
    }

    fun label(label: String): CategoryBuilder {
        category.label = label
        return this
    }

    fun image(image: String): CategoryBuilder {
        category.image = image
        return this
    }

    fun link(link: String): CategoryBuilder {
        category.link = link
        return this
    }

    fun build(): Category {
        return category
    }

}
