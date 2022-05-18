package com.handstandsam.shoppingapp.network

import com.handstandsam.shoppingapp.di.Response
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import kotlinx.coroutines.Deferred

interface ShoppingService {

    //    @POST("login")
    fun login(loginRequest: LoginRequest): Deferred<Response<User>>

    //    @GET("categories")
    fun categories(): Deferred<Response<List<Category>>>

    //    @GET("category/{categoryName}/items")
    fun getItemsForCategory(categoryName: String): Deferred<Response<List<Item>>>
}
