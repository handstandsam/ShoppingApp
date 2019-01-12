package com.handstandsam.shoppingapp.features.category

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Item

class CategoryActivity : LoggedInActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var recyclerViewAdapter: CategoryRVAdapter

    private val view: CategoryView = MyCategoryView()

    private var presenter: CategoryPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.categories)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerViewAdapter = CategoryRVAdapter()
        recyclerView.adapter = recyclerViewAdapter
        presenter = CategoryPresenter(
            view = view,
            itemRepo = appGraph.networkGraph.itemRepo
        )
    }

    interface CategoryView {

        val context: Context

        fun showItems(items: List<Item>)

        fun setActionBarTitle(title: String)

        fun showNetworkError(message: String?)
    }

    inner class MyCategoryView : CategoryView {

        override val context: Context
            get() = this@CategoryActivity.applicationContext

        override fun showItems(items: List<Item>) {
            recyclerViewAdapter.setItems(items)
        }

        override fun setActionBarTitle(title: String) {
            supportActionBar?.title = title
        }

        override fun showNetworkError(message: String?) {
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

    override fun onResume() {
        super.onResume()
        presenter!!.onResume(intent)
    }
}
