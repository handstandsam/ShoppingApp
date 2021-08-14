package com.handstandsam.shoppingapp.network

import com.handstandsam.shoppingapp.mockaccount.EndpointUrls
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.models.User
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockNetworkManager(
    private val networkConfig: NetworkConfig,
    private val mockAccount: MockAccount
) {

    private var moshi = Moshi.Builder().build()

    private fun processCategoriesRequest(): MockResponse {
        val json = moshi.adapter(List::class.java).toJson(mockAccount.getCategories())
        return MockResponse().apply {
            setBody(json)
        }
    }

    private fun processCategoryRequest(requestUrl: String): MockResponse {
        mockAccount.itemsByCategory.keys.forEach { categoryId ->
            if (requestUrl.contains(EndpointUrls.getItemsForCategoryUrl(categoryId))) {
                val items = mockAccount.itemsByCategory[categoryId]
                val json = moshi.adapter(List::class.java).toJson(items)
                return MockResponse().apply {
                    setBody(json)
                }
            }
        }

        return MockResponse().apply {
            setBody("Category Not Found")
            setResponseCode(404)
        }
    }

    private suspend fun startServer() {

        val server = MockWebServer()
        server.start(networkConfig.port)
        println("Server started on port: ${server.port}")

        server.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                println("Request: $request")
                val url = request.requestUrl.toString()
                return when {
                    url.contains(EndpointUrls.loginUrl) -> {
                        val json = moshi.adapter(User::class.java).toJson(mockAccount.getUser())
                        MockResponse().apply {
                            setBody(json)
                        }
                    }
                    url.contains(EndpointUrls.categoriesUrl) -> {
                        processCategoriesRequest()
                    }
                    url.contains("/category/") -> {
                        processCategoryRequest(url)
                    }
                    else -> {
                        MockResponse().apply {
                            setResponseCode(404)
                        }
                    }
                }
            }
        })
    }

    init {
        if (networkConfig.isMockServer) {
            CoroutineScope(Dispatchers.IO).launch {
                startServer()
            }
        }
    }
}