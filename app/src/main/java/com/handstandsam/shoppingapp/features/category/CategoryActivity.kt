package com.handstandsam.shoppingapp.features.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.compose.CategoryScreen
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.models.Category
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoryActivity : LoggedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val categoryViewModel = ViewModelProvider(this, graph.viewModelFactory)
            .get(CategoryViewModel::class.java)

        setContent {
            CategoryScreen(
                itemsInCart = graph
                    .sessionGraph
                    .shoppingCart
                    .itemsInCart,
                categoryViewModel = categoryViewModel,
                showCartClicked = { startCheckoutActivity() },
                logoutClicked = { logout() },
                homeUpClicked = { onBackPressed() }
            )
        }

        lifecycleScope.launchWhenCreated {
            categoryViewModel.sideEffects
                .onEach {
                    when (it) {
                        is CategoryViewModel.SideEffect.LaunchItemDetailActivity -> {
                            ItemDetailActivity.launch(this@CategoryActivity, it.item)
                        }
                    }
                }
                .launchIn(this)
        }


        val extras = intent.extras
        val categoryLabel = extras!!.getString(BUNDLE_PARAM_CATEGORY)!!
        categoryViewModel.send(CategoryViewModel.Intention.CategoryLabelSet(categoryLabel))

    }

    companion object {
        const val BUNDLE_PARAM_CATEGORY = "category"

        fun launch(context: Context, category: Category) {
            val intent = Intent(context, CategoryActivity::class.java)
            val extras = Bundle()
            extras.putString(BUNDLE_PARAM_CATEGORY, category.label)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }
}