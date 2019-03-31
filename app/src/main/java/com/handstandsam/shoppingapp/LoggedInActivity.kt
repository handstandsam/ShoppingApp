package com.handstandsam.shoppingapp


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.SessionGraph
import com.handstandsam.shoppingapp.features.checkout.CheckoutActivity
import com.handstandsam.shoppingapp.features.login.LoginActivity
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.CheckoutCart
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


open class LoggedInActivity : AppCompatActivity(),
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    protected val graph: AppGraph get() = application.graph()

    private val sessionGraph: SessionGraph get() = graph.sessionGraph

    private val sessionManager: SessionManager get() = sessionGraph.sessionManager

    private val checkoutCart: CheckoutCart get() = sessionGraph.checkoutCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!sessionManager.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.logged_in_menu, menu)
        LoggedInMenuUi(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private inner class LoggedInMenuUi(menu: Menu) {
        private val alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item)

        private val rootView = (alertMenuItem.actionView as FrameLayout).apply {
            setOnClickListener {
                startActivity(Intent(this@LoggedInActivity, CheckoutActivity::class.java))
            }
        }

        private val redCircle: FrameLayout = rootView.findViewById(R.id.view_alert_red_circle)

        private val countTextView: TextView = rootView.findViewById(R.id.view_alert_count_textview)

        init {
            launch {
                checkoutCart
                    .itemsInCartStream()
                    .consumeEach { itemsInCart ->
                        updateItemCount(itemsInCart)
                    }
            }
        }

        private fun updateItemCount(itemsInCart: List<Item>) {
            val itemsInCartCount = itemsInCart.size
            if (itemsInCartCount == 0) {
                redCircle.visibility = View.GONE
            } else {
                redCircle.visibility = View.VISIBLE
                countTextView.text = itemsInCartCount.toString()
            }
        }
    }
}
