package com.handstandsam.shoppingapp.di

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient

interface NetworkGraph {
    val categoryRepo: CategoryRepo
    val itemRepo: ItemRepo
    val userRepo: UserRepo
}

open class BaseNetworkGraph(
    networkConfig: NetworkConfig,
    interceptors: List<Interceptor> = listOf()
) : NetworkGraph {

    private val okHttpClientBuilder = OkHttpClient.Builder()

    init {
        interceptors.forEach { interceptor ->
            okHttpClientBuilder.addInterceptor(interceptor)
        }
    }

    private val okHttpClient = okHttpClientBuilder.build()


    val ktorClient = createKtorClient(okHttpClient)


    override val userRepo: UserRepo = object : UserRepo {
        override suspend fun login(loginRequest: LoginRequest): Response<User> {
            val response = ktorClient.request("${networkConfig.fullUrl}login") {
                method = HttpMethod.Post
                setBody(loginRequest)
            }

            if (response.status == HttpStatusCode.OK) {
                val user = response.body<User>()
                return Response.Success(user)

            }
            return Response.Failure()
        }
    }

    override val categoryRepo: CategoryRepo = object : CategoryRepo {
        override suspend fun getCategories(): Response<List<Category>> {
            val response = ktorClient.request("${networkConfig.fullUrl}categories") {
                method = HttpMethod.Get
            }

            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.body<List<Category>>()
                return Response.Success(responseBody)

            }
            return Response.Failure()
        }
    }

    override val itemRepo: ItemRepo = object : ItemRepo {
        override suspend fun getItemsForCategory(categoryLabel: String): Response<List<Item>> {
            val itemsForCategoryUrl = "${networkConfig.fullUrl}category/${categoryLabel}/items"
            val response =
                ktorClient.request(itemsForCategoryUrl) {
                    method = HttpMethod.Get
                }

            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.body<List<Item>>()
                return Response.Success(responseBody)
            } else {
                return Response.Failure()
            }
        }
    }

    companion object {
        fun createKtorClient(okHttpClient: OkHttpClient): HttpClient {
            return HttpClient(OkHttp) {
                engine {
                    preconfigured = okHttpClient
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    KotlinxSerializationConverter(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    ).apply {
                        listOf(
                            ContentType.Application.Json,
                            ContentType("binary", "octet-stream"), // S3 Bucket
                        ).forEach { contentType ->
                            register(contentType, this)
                        }
                    }
                }
            }
        }
    }
}