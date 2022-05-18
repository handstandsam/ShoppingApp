package com.handstandsam.shoppingapp.network

sealed interface Response<T> {
    class Success<T> : Response<T>
    object Failure : Response<Unit>
}