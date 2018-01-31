package com.handstandsam.shoppingapp.mockaccount


import android.content.Context
import android.os.StrictMode
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.di.NetworkModule
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.repository.CategoryRepository
import com.handstandsam.shoppingapp.repository.ItemRepository
import com.handstandsam.shoppingapp.repository.SessionManager
import com.squareup.moshi.Moshi
import timber.log.Timber
import javax.inject.Inject

class Stubberator(context: Context) {

    @Inject
    lateinit var itemRepository: ItemRepository

    @Inject
    lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var sessionManager: SessionManager

    private var moshi = Moshi.Builder().build()

    private lateinit var wireMockServer: WireMockServer

    private var applicationContext: Context

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if (NetworkModule.USE_LOCAL_SERVER) {
            wireMockServer = WireMockServer(wireMockConfig().port(NetworkModule.LOCALHOST_PORT))
            wireMockServer.start()
            wireMockServer.resetMappings()
        } else {
            WireMock.configureFor(NetworkModule.REMOTE_EMULATOR_ENDPOINT_HOST, NetworkModule.REMOTE_PORT)
            WireMock.reset()
        }

        Timber.d("new Stubberator()")
        this.applicationContext = context.applicationContext
        (applicationContext as MyAbstractApplication).appComponent.inject(this)
    }

    fun stubFor(mappingBuilder: MappingBuilder): StubMapping {
        return if (NetworkModule.USE_LOCAL_SERVER) {
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
        stubFor(StubMappings.categories.willReturn(WireMock.aResponse().withStatus(200).withBody(json)))
    }

    fun stubItems(mockAccount: MockAccount) {
        mockAccount.itemsByCategory.keys.forEach({ categoryId ->
            val items = mockAccount.itemsByCategory[categoryId]
            val json = moshi.adapter(List::class.java).toJson(items)
            stubFor(StubMappings.getItemsForCategory(categoryId).willReturn(WireMock.aResponse().withStatus(200).withBody(json)))

        })
    }

    fun stubLogin(mockAccount: MockAccount) {
        val json = moshi.adapter(User::class.java).toJson(mockAccount.getUser())
        stubFor(StubMappings.login().willReturn(WireMock.aResponse()
                .withStatus(200).withBody(json)))
    }

}
