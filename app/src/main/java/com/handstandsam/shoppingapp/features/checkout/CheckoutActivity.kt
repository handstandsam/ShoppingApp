package com.handstandsam.shoppingapp.features.checkout

import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutActivity : LoggedInActivity() {

    val cart get() = graph.sessionGraph.checkoutCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_checkout)
        MyCheckoutUi()
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
            else -> return super.onContextItemSelected(item)
        }
    }

    private inner class MyCheckoutUi {

        private val itemCountTextView: TextView = findViewById(R.id.item_count)
        private val itemsText: TextView = findViewById(R.id.items)
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
                cart.itemsInCartStream().consumeEach {
                    withContext(Dispatchers.Main) {
                        itemCountTextView.text = CartContentsMessage.itemCountText(cart.items)
                        itemsText.text = CartContentsMessage.itemsText(cart.items)
                    }
                }
            }
        }

    }
}
