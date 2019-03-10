package com.handstandsam.shoppingapp

import android.content.Context
import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.repository.UserRepo
import io.reactivex.Single

fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return LiveNetworkGraph(applicationContext)
}