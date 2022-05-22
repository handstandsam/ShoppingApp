package com.handstandsam.shoppingapp.multiplatform

import com.handstandsam.shoppingapp.network.Response
import kotlin.js.Promise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

@JsExport
class JsMultiplatformApi {
    private val ktorHttpClient = KtorHttpClient()

    val networkGraph = ktorHttpClient.networkGraph

    fun getCategoriesAsync(): Promise<Array<CategoryData>> = GlobalScope.promise {
        val categoriesResponse = networkGraph.categoryRepo.getCategories()
        println("Response: $categoriesResponse")
        when (categoriesResponse) {
            is Response.Success -> {
                println("Success ${categoriesResponse.body}")
                val categories = categoriesResponse.body
                categories.map { CategoryData(it.label, it.image) }.toTypedArray()
            }
            is Response.Failure -> {
                error("OOPS")
            }
        }
    }
}