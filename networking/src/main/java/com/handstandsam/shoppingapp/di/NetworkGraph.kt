package com.handstandsam.shoppingapp.di

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.network.ShoppingService
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.NetworkCategoryRepo
import com.handstandsam.shoppingapp.repository.NetworkItemRepo
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.repository.NetworkUserRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.coroutines.Deferred
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

    private val shoppingService: ShoppingService = object : ShoppingService {
        override fun login(loginRequest: LoginRequest): Deferred<Response<User>> {
            TODO("Not yet implemented")
        }

        override fun categories(): Deferred<Response<List<Category>>> {
            TODO("Not yet implemented")
        }

        override fun getItemsForCategory(categoryName: String): Deferred<Response<List<Item>>> {
            TODO("Not yet implemented")
        }

    }

    override val categoryRepo: CategoryRepo = NetworkCategoryRepo(shoppingService)

    override val itemRepo: ItemRepo = NetworkItemRepo(shoppingService)

    val ktorClient = createKtorClient(okHttpClient)

    override val userRepo: UserRepo = NetworkUserRepo(shoppingService, ktorClient)

    companion object {
        fun createKtorClient(okHttpClient: OkHttpClient): HttpClient {
            return HttpClient(OkHttp) {
                engine {
                    preconfigured = okHttpClient
                }
                install(ContentNegotiation) {
                    register(
//                        contentType = ContentType.Application.Json,
                        contentType = ContentType.Any,
                        converter = KotlinxSerializationConverter(
                            Json {
                                prettyPrint = true
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )
                    )
                }
            }
        }
    }
}