package com.handstandsam.shoppingapp.mockaccount


import android.net.Uri

object EndpointUrls {

    const val categoriesUrl: String = "/categories"

    fun getItemsForCategoryUrl(categoryId: String): String {
        return "/category/" + Uri.encode(categoryId) + "/items"
    }

    const val loginUrl: String = "/login"

}
