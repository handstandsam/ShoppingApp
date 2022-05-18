package com.handstandsam.shoppingapp.network

sealed interface Response<T> {
    class Success<T>(val body : T) : Response<T>
    class Failure<T> : Response<T>
}