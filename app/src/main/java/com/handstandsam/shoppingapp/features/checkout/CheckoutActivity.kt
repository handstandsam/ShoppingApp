package com.handstandsam.shoppingapp.features.checkout

import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutActivity : LoggedInActivity() {

    val cart get() = graph.sessionGraph.shoppingCart

    private lateinit var recyclerView: RecyclerView

    private lateinit var recyclerViewAdapter: CheckoutRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_checkout)
        MyCheckoutUi()

        recyclerView = findViewById(R.id.checkout_items)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerViewAdapter = CheckoutRVAdapter()
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.checkout_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.empty_cart -> {
                cart.empty()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private inner class MyCheckoutUi {

        private val itemCountTextView: TextView = findViewById(R.id.item_count)
        private val checkoutButton = findViewById<AppCompatButton>(R.id.checkout_button).apply {
            setOnClickListener {
                val toast = Toast.makeText(
                    applicationContext,
                    "TODO: Checkout Feature Not Implemented",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        init {
            launch {
                cart.itemsInCartChannel().consumeEach { itemsInCart: List<ItemWithQuantity> ->
                    withContext(Dispatchers.Main) {
                        itemCountTextView.text =
                            itemsInCart.totalItemCount().toString() + " item(s) in your cart."
                        recyclerViewAdapter.setItems(itemsInCart)
                    }
                }
            }
        }

    }
}
