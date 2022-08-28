package com.handstandsam.shoppingapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.SessionGraph
import com.handstandsam.shoppingapp.features.checkout.CheckoutActivity
import com.handstandsam.shoppingapp.features.login.LoginActivity
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount
import com.handstandsam.shoppingapp.repository.AndroidSessionManager
import kotlinx.coroutines.launch


open class LoggedInActivity : AppCompatActivity() {

    protected val graph: AppGraph get() = application.graph()

    private val sessionGraph: SessionGraph get() = graph.sessionGraph

    private val sessionManager: AndroidSessionManager get() = sessionGraph.sessionManager

    private val checkoutCart: ShoppingCart get() = sessionGraph.shoppingCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!sessionManager.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }


    private fun initLoggedInMenuUi(
        menu: Menu,
        shoppingCart: ShoppingCart,
        startCheckoutActivity: () -> Unit
    ) {
        val alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item)

        val rootView = (alertMenuItem.actionView as FrameLayout).apply {
            setOnClickListener { startCheckoutActivity() }
        }
        val redCircle: FrameLayout = rootView.findViewById(R.id.view_alert_red_circle)

        val countTextView: TextView = rootView.findViewById(R.id.view_alert_count_textview)

        fun updateItemCount(itemsInCart: List<ItemWithQuantity>) {
            val itemsInCartCount = itemsInCart.size
            if (itemsInCartCount == 0) {
                redCircle.visibility = View.GONE
            } else {
                redCircle.visibility = View.VISIBLE
                countTextView.text = itemsInCart.totalItemCount().toString()
            }
        }

        lifecycleScope.launchWhenCreated {
            shoppingCart
                .itemsInCart
                .collect { itemsInCart ->
                    updateItemCount(itemsInCart)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.logged_in_menu, menu)
        initLoggedInMenuUi(menu, checkoutCart) {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logout()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    protected fun logout() {
        val launchIntent = Intent(this, LoginActivity::class.java)
        lifecycleScope.launch {
            sessionManager.logout()
            startActivity(launchIntent)
            finish()
        }
    }

    protected fun startCheckoutActivity() {
        startActivity(Intent(this, CheckoutActivity::class.java))
    }
}
