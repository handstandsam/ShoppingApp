package com.handstandsam.shoppingapp.features.checkout

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.appGraph
import com.handstandsam.shoppingapp.di.AppGraph

class CheckoutActivity : LoggedInActivity() {

    private val appGraph: AppGraph by lazy { application.appGraph() }

    lateinit var itemCountTextView: TextView

    lateinit var itemsText: TextView

    private val checkoutView: CheckoutView = MyCheckoutView()

    private var presenter: CheckoutPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setTitle(R.string.cart)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_checkout)
        itemCountTextView = findViewById(R.id.item_count)
        itemsText = findViewById(R.id.items)
        presenter = CheckoutPresenter(
            view = checkoutView,
            cart = appGraph.sessionGraph.checkoutCart
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.checkout_menu, menu)
        return true
    }

    interface CheckoutView {

        fun setItemCountText(text: String)

        fun setItemsText(text: String)
    }

    inner class MyCheckoutView : CheckoutView {

        override fun setItemCountText(text: String) {
            itemCountTextView.text = text
        }

        override fun setItemsText(text: String) {
            itemsText.text = text
        }

    }

    override fun onResume() {
        super.onResume()
        presenter!!.onResume()
    }
}
