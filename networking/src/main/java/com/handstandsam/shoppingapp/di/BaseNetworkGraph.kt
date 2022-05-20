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
import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

open class BaseNetworkGraph(
    networkConfig: NetworkConfig,
    ktorClient: HttpClient,
) : NetworkGraph {

    init {
        configureKtorClient(ktorClient)
    }

    override val userRepo: UserRepo = object : UserRepo {
        override suspend fun login(loginRequest: LoginRequest): Response<User> {
            val response = ktorClient.request("${networkConfig.fullUrl}login") {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
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
                println("RESPONSE")
                println(response)
                val json: String = response.bodyAsText()
                println(json)
                val decoded = Json.decodeFromString<List<Category>>(json)
                println(decoded)
                return Response.Success(decoded)
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
                val responseBody = Json.decodeFromString<List<Item>>(response.bodyAsText())
                return Response.Success(responseBody)
            } else {
                return Response.Failure()
            }
        }
    }

    companion object {
        fun configureKtorClient(ktorClient: HttpClient): HttpClient {
            return ktorClient.config {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println(message)
                        }
                    }
                    level = LogLevel.HEADERS
                }
                install(ContentNegotiation) {
                    val jsonConfig = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                    val contentTypes = listOf(
                        ContentType.Application.Json,
                        ContentType("binary", "octet-stream"), // S3 Bucket
                    )

                    contentTypes.forEach { contentType ->
                        println("Registering: $contentType")
                        register(contentType, KotlinxSerializationConverter(jsonConfig))
                    }
                }
            }
        }
    }
}