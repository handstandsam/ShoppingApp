package com.handstandsam.shoppingapp.network

import com.handstandsam.shoppingapp.mockaccount.EndpointUrls
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.NetworkConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockNetworkManager(
    private val networkConfig: NetworkConfig,
    private val mockAccount: MockAccount
) {

    private fun processCategoriesRequest(): MockResponse {

        val json = Json.encodeToString(mockAccount.getCategories())
        return MockResponse().apply {
            addApplicationJsonContentTypeHeader()
            setBody(json)
        }
    }

    private fun processCategoryRequest(requestUrl: String): MockResponse {
        mockAccount.itemsByCategory.keys.forEach { categoryId ->
            if (requestUrl.contains(EndpointUrls.getItemsForCategoryUrl(categoryId))) {
                val items = mockAccount.itemsByCategory[categoryId]
                val json = Json.encodeToString(items)
                return MockResponse().apply {
                    addApplicationJsonContentTypeHeader()
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

        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                println("Request: $request")
                val url = request.requestUrl.toString()
                return when {
                    url.contains(EndpointUrls.loginUrl) -> {
                        val json = Json.encodeToString(mockAccount.getUser())
                        MockResponse().apply {
                            addApplicationJsonContentTypeHeader()
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
        }
    }

    init {
        if (networkConfig.isMockServer) {
            CoroutineScope(Dispatchers.IO).launch {
                startServer()
            }
        }
    }
}

private fun MockResponse.addApplicationJsonContentTypeHeader() {
    addHeader("content-type", "application/json")
}
