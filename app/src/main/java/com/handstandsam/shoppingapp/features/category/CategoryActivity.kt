package com.handstandsam.shoppingapp.features.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CategoryActivity : LoggedInActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var recyclerViewAdapter: CategoryRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.categories)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerViewAdapter = CategoryRVAdapter()
        recyclerView.adapter = recyclerViewAdapter

        Ui(graph.networkGraph.itemRepo)
    }

    inner class Ui(itemRepo: ItemRepo) {
        init {
            val extras = intent.extras
            val (label) = extras!!.get(BUNDLE_PARAM_CATEGORY) as Category
            setActionBarTitle(label)
            lifecycleScope.launchWhenCreated {
                val itemsResult = withContext(Dispatchers.Default) {
                    itemRepo.getItemsForCategory(label)
                }
                when (itemsResult) {
                    is NetworkResult.Success -> {
                        showItems(itemsResult.body)
                    }
                    is NetworkResult.Failure -> {
                        Timber.w("Networking Error", itemsResult.errorResponse)
                        showNetworkError(itemsResult.errorResponse.toString())
                    }
                }.exhaustive
            }
        }

        val context: Context
            get() = this@CategoryActivity.applicationContext

        fun showItems(items: List<Item>) {
            recyclerViewAdapter.setItems(items)
        }

        fun setActionBarTitle(title: String) {
            supportActionBar?.title = title
        }

        fun showNetworkError(message: String?) {
            val builder = AlertDialog.Builder(
                this@CategoryActivity
            ).setTitle("Networking Error")

            if (message != null) {
                builder.setMessage(message)
            }
            builder
                .setPositiveButton("OK") { _, i ->
                    this@CategoryActivity.finish()
                }
                .show()
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