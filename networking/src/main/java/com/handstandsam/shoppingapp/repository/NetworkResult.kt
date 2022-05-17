package com.handstandsam.shoppingapp.repository

sealed interface NetworkResult<T> {
    data class Success<T>(val body: T) : NetworkResult<T>
    class Failure<T> : NetworkResult<T>
}