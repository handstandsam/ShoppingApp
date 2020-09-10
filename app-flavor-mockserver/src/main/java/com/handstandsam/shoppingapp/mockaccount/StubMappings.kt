package com.handstandsam.shoppingapp.mockaccount


import android.net.Uri
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*

object StubMappings {

    val categories: MappingBuilder
        @JvmStatic
        get() = get(urlEqualTo("/categories"))

    @JvmStatic
    fun getItemsForCategory(categoryId: String): MappingBuilder {
        return get(urlEqualTo("/category/" + Uri.encode(categoryId) + "/items"))
    }

    @JvmStatic
    fun login(): MappingBuilder {
        return post(urlEqualTo("/login"))
    }

}
