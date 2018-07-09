package com.handstandsam.shoppingapp.network

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingService {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Single<User>

    @GET("categories")
    fun categories(): Single<List<Category>>

    @GET("category/{categoryName}/items")
    fun getItemsForCategory(@Path("categoryName") categoryName: String): Single<List<Item>>
}
