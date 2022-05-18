package com.handstandsam.shoppingapp.mockaccount



object EndpointUrls {

    const val categoriesUrl: String = "/categories"

    fun getItemsForCategoryUrl(categoryId: String): String {
        // I replaced Android Uri Encode with this "workaround"
        // Issue https://github.com/handstandsam/ShoppingApp/issues/55
        val encodedCategoryId = categoryId.replace(" ", "%20")
        return "/category/$encodedCategoryId/items"
    }

    const val loginUrl: String = "/login"

}
