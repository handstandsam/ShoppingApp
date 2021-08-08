package com.handstandsam.shoppingapp.features.home

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.compose.CategoryView
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.models.Category
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeActivity : LoggedInActivity() {

    // Example of `get() =`
    private val sessionManager get() = graph.sessionGraph.sessionManager

    // Example of `by lazy`
    private val categoryRepo by lazy { graph.networkGraph.categoryRepo }

    private val categoryListView get() = findViewById<ComposeView>(R.id.compose_frame_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Categories"
        setContentView(R.layout.activity_home)

        val homeViewModel = ViewModelProvider(this, graph.homeViewModelFactory)
            .get(HomeViewModel::class.java)

        categoryListView.setContent {
            val state by homeViewModel.states.collectAsState(initial = HomeViewModel.State())
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = state.welcomeMessage,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    state.categories.forEach { category ->
                        item {
                            CategoryView(category) {
                                homeViewModel.send(HomeViewModel.Intention.CategoryClicked(category))
                            }
                        }
                    }
                }
            }

        }

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
