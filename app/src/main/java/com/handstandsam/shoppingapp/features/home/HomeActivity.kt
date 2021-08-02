package com.handstandsam.shoppingapp.features.home

import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.compose.CategoryView
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeActivity : LoggedInActivity() {

    // Example of `get() =`
    private val sessionManager get() = graph.sessionGraph.sessionManager

    // Example of `by lazy`
    private val categoryRepo by lazy { graph.networkGraph.categoryRepo }

    private lateinit var welcomeMessageText: TextView

    private val categoryListView get() = findViewById<ComposeView>(R.id.compose_frame_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Categories"
        setContentView(R.layout.activity_home)
        welcomeMessageText = findViewById(R.id.welcome_message)

        val homeViewModel = ViewModelProvider(this, graph.homeViewModelFactory)
            .get(HomeViewModel::class.java)

        categoryListView.setContent {
            val state = homeViewModel.states.collectAsState(initial = HomeViewModel.State())
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val list: List<Category> = state.value.categories
                items(list) { category ->
                    CategoryView(category) {
                        homeViewModel.send(HomeViewModel.Intention.CategoryClicked(category))
                    }
                }
            }
        }

        // Support view state along with Compose
        homeViewModel.states
            .onEach { state ->
                welcomeMessageText.text = state.welcomeMessage
            }
            .launchIn(lifecycleScope)


        homeViewModel.sideEffects
            .onEach {
                when (it) {
                    is HomeViewModel.SideEffect.LaunchCategoryActivity -> {
                        CategoryActivity.launch(this@HomeActivity, it.category)
                    }
                }
            }
            .launchIn(lifecycleScope)

    }
}
