package com.handstandsam.shoppingapp.network

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingService {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Deferred<User>

    @GET("categories")
    fun categories(): Deferred<List<Category>>

    @GET("category/{categoryName}/items")
    fun getItemsForCategory(@Path("categoryName") categoryName: String): Deferred<List<Item>>
}
