package com.handstandsam.shoppingapp.di

data class AppGraph(
    val sessionGraph: SessionGraph,
    val networkGraph: NetworkGraph
) {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: AppGraph
    }
}