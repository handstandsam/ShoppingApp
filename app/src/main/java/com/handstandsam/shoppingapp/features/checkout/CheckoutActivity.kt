package com.handstandsam.shoppingapp.features.checkout

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.di.AppComponent

class CheckoutActivity : LoggedInActivity() {

    lateinit var itemCountTextView: TextView

    lateinit var itemsText: TextView

    private var view: CheckoutView? = null

    private var presenter: CheckoutPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setTitle(R.string.cart)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_checkout)
        itemCountTextView = findViewById(R.id.item_count)
        itemsText = findViewById(R.id.items)
        (application as MyAbstractApplication).appComponent.inject(this)

        view = MyCheckoutView()
        presenter = CheckoutPresenter(view!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.checkout_menu, menu)
        return true
    }

    interface CheckoutView {

        val appComponent: AppComponent

        fun setItemCountText(text: String)

        fun setItemsText(text: String)
    }

    inner class MyCheckoutView : CheckoutView {

        override val appComponent: AppComponent
            get() = (application as MyAbstractApplication).appComponent

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
