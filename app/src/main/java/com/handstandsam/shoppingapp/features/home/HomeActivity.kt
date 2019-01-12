package com.handstandsam.shoppingapp.features.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Category

class HomeActivity : LoggedInActivity() {

    private lateinit var presenter: HomePresenter

    private var recyclerView: RecyclerView? = null

    private var welcomeMessageText: TextView? = null

    private lateinit var homeView: HomeView

    private var recyclerViewAdapter: HomeRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Categories"
        setContentView(R.layout.activity_home)
        welcomeMessageText = findViewById(R.id.welcome_message)
        recyclerView = findViewById(R.id.categories)
        recyclerView!!.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewAdapter = HomeRVAdapter()
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        homeView = MyHomeView()
        presenter = HomePresenter(
            view = homeView,
            sessionManager = appGraph.sessionGraph.sessionManager,
            categoryRepo = appGraph.networkGraph.categoryRepo
        )
    }

    interface HomeView {

        val context: Context

        fun showCategories(categories: List<Category>)

        fun setWelcomeMessage(welcomeStr: String)
    }

    inner class MyHomeView : HomeView {

        override val context: Context
            get() = this@HomeActivity

        override fun showCategories(categories: List<Category>) {
            recyclerViewAdapter!!.setData(categories)
        }

        override fun setWelcomeMessage(welcomeStr: String) {
            welcomeMessageText!!.text = welcomeStr
        }
    }


    override fun onResume() {
        super.onResume()
        presenter.onResume(intent)
    }
}
