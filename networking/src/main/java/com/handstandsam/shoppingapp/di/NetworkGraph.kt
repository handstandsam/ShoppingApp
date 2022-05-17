package com.handstandsam.shoppingapp.di
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.network.ShoppingService
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.NetworkCategoryRepo
import com.handstandsam.shoppingapp.repository.NetworkItemRepo
import com.handstandsam.shoppingapp.repository.NetworkUserRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

    private val moshi = Moshi.Builder().build()

    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    private val coroutinesCallAdapterFactory = CoroutineCallAdapterFactory()

    private val okHttpClient = okHttpClientBuilder.build()

    private val retrofitBuilder: Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(networkConfig.fullUrl)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(coroutinesCallAdapterFactory)
            .client(okHttpClient)

    private val retrofit: Retrofit = retrofitBuilder.build()

    private val shoppingService: ShoppingService = retrofit.create(ShoppingService::class.java)

    override val categoryRepo: CategoryRepo = NetworkCategoryRepo(shoppingService)

    override val itemRepo: ItemRepo = NetworkItemRepo(shoppingService)

    val ktorClient = HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient
        }
    }

    override val userRepo: UserRepo = NetworkUserRepo(shoppingService, ktorClient)


}