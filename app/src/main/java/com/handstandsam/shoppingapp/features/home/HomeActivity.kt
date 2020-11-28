package com.handstandsam.shoppingapp.features.home

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.compose.CategoryListView
import com.handstandsam.shoppingapp.models.Category

class HomeActivity : LoggedInActivity() {

    // Example of `get() =`
    private val sessionManager get() = graph.sessionGraph.sessionManager

    // Example of `by lazy`
    private val categoryRepo by lazy { graph.networkGraph.categoryRepo }

    private lateinit var presenter: HomePresenter

    private var welcomeMessageText: TextView? = null

    private val categoryListView get() = findViewById<CategoryListView>(R.id.compose_frame_layout)

    private lateinit var homeView: HomeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Categories"
        setContentView(R.layout.activity_home)
        welcomeMessageText = findViewById(R.id.welcome_message)

        homeView = MyHomeView()
        presenter = HomePresenter(
            view = homeView,
            sessionManager = sessionManager,
            categoryRepo = categoryRepo,
            scope = lifecycleScope
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
            categoryListView.categories.value = categories
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
