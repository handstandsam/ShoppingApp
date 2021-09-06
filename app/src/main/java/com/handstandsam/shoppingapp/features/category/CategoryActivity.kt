package com.handstandsam.shoppingapp.features.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.compose.CategoryScreen
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailPresenter
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import timber.log.Timber

class CategoryActivity : LoggedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            supportActionBar?.hide()

            val categoryViewModel = ViewModelProvider(this, graph.viewModelFactory)
                .get(CategoryViewModel::class.java)

            val extras = intent.extras
            val category = extras!!.get(BUNDLE_PARAM_CATEGORY) as Category
            categoryViewModel.send(CategoryViewModel.Intention.CategoryLabelSet(category.label))

            setContent {
                CategoryScreen(
                    itemsInCart = graph
                        .sessionGraph
                        .shoppingCart
                        .itemsInCart,
                    categoryViewModel = categoryViewModel,
                    checkoutClicked = { startCheckoutActivity() },
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

    }

    companion object {
        const val BUNDLE_PARAM_CATEGORY = "category"

        fun launch(context: Context, category: Category) {
            val intent = Intent(context, CategoryActivity::class.java)
            val extras = Bundle()
            extras.putSerializable(BUNDLE_PARAM_CATEGORY, category)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }
}