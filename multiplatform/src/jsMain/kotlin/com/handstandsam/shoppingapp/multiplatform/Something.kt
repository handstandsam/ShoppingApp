package com.handstandsam.shoppingapp.multiplatform

import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount

@JsExport
class Something {
    val mockAccount = ProduceMockAccount()

    fun getCategoryNames(): Array<CategoryData> {
        return mockAccount.getCategories().map {
            CategoryData(
                label = it.label,
                image = it.image,
            )
        }.toTypedArray()
    }
}

@JsExport
data class CategoryData(val label: String, val image: String)