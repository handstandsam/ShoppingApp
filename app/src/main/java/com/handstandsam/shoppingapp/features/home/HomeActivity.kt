package com.handstandsam.shoppingapp.features.home

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.compose.CategoryView
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.models.Category

class HomeActivity : LoggedInActivity() {

    // Example of `get() =`
    private val sessionManager get() = graph.sessionGraph.sessionManager

    // Example of `by lazy`
    private val categoryRepo by lazy { graph.networkGraph.categoryRepo }

    private lateinit var presenter: com.handstandsam.shoppingapp.features.home.HomePresenter

    private var welcomeMessageText: TextView? = null

    private val categoryListView get() = findViewById<ComposeView>(R.id.compose_frame_layout)

    private lateinit var homeView: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Categories"
        setContentView(R.layout.activity_home)
        welcomeMessageText = findViewById(R.id.welcome_message)

        homeView = MyHomePresenter()
        presenter = HomePresenter(
            view = homeView,
            sessionManager = sessionManager,
            categoryRepo = categoryRepo,
            scope = lifecycleScope
        )
    }

    interface HomePresenter {

        val context: Context

        fun showCategories(categories: List<Category>)

        fun setWelcomeMessage(welcomeStr: String)
    }

    val categories = mutableStateOf(listOf<Category>())

    inner class MyHomePresenter : HomePresenter {

        override val context: Context
            get() = this@HomeActivity

        override fun showCategories(categories: List<Category>) {
            categoryListView.setContent {
                LazyColumn(
                    modifier = Modifier
                        .background(Color.Green)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    items(categories) { category ->
                        CategoryView(category) {
                            CategoryActivity.launch(context, category)
                        }
                    }
                }
            }
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
