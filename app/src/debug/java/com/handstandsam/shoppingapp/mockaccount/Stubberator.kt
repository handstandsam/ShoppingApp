package com.handstandsam.shoppingapp.mockaccount


import android.os.StrictMode
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.User
import com.squareup.moshi.Moshi
import timber.log.Timber

class Stubberator(val useLocalServer: Boolean) {

    private var moshi = Moshi.Builder().build()

    private lateinit var wireMockServer: WireMockServer

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if (useLocalServer) {
            wireMockServer = WireMockServer(wireMockConfig().port(NetworkConstants.LOCALHOST_PORT))
            wireMockServer.start()
            wireMockServer.resetMappings()
        } else {
            WireMock.configureFor(
                NetworkConstants.REMOTE_EMULATOR_ENDPOINT_HOST,
                NetworkConstants.REMOTE_PORT
            )
            WireMock.reset()
        }

        Timber.d("new Stubberator()")
    }

    fun stubFor(mappingBuilder: MappingBuilder): StubMapping {
        return if (NetworkConstants.USE_LOCAL_SERVER) {
            wireMockServer.stubFor(mappingBuilder)
        } else {
            WireMock.stubFor(mappingBuilder)
        }
    }

    fun stubItAll(mockAccount: MockAccount) {
        stubCategories(mockAccount)
        stubLogin(mockAccount)
        stubItems(mockAccount)
    }

    fun stubCategories(mockAccount: MockAccount) {
        val json = moshi.adapter(List::class.java).toJson(mockAccount.getCategories())
        stubFor(
            StubMappings.categories.willReturn(
                WireMock.aResponse().withStatus(200).withBody(
                    json
                )
            )
        )
    }

    fun stubItems(mockAccount: MockAccount) {
        mockAccount.itemsByCategory.keys.forEach { categoryId ->
            val items = mockAccount.itemsByCategory[categoryId]
            val json = moshi.adapter(List::class.java).toJson(items)
            stubFor(
                StubMappings.getItemsForCategory(categoryId).willReturn(
                    WireMock.aResponse().withStatus(
                        200
                    ).withBody(json)
                )
            )
        }
    }

    fun stubLogin(mockAccount: MockAccount) {
        val json = moshi.adapter(User::class.java).toJson(mockAccount.getUser())
        stubFor(
            StubMappings.login().willReturn(
                WireMock.aResponse()
                    .withStatus(200).withBody(json)
            )
        )
    }

}

private object NetworkConstants {
    var LOCALHOST_PORT = 8080
    val LOCALHOST_ENDPOINT = "http://localhost:" + LOCALHOST_PORT
    val S3_ENDPOINT = "https://shopping-app.s3.amazonaws.com"

    var USE_LOCAL_SERVER = true

    val REMOTE_PORT = 8080
    val REMOTE_EMULATOR_ENDPOINT_HOST = "10.0.2.2"
    var LAPTOP_FROM_EMULATOR_ENDPOINT = "http://$REMOTE_EMULATOR_ENDPOINT_HOST:$REMOTE_PORT"
}