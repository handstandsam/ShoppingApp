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
import com.handstandsam.shoppingapp.repository.CheckoutCart
import com.handstandsam.shoppingapp.repository.ItemWithQuantity
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    private fun initLoggedInMenuUi(
        menu: Menu,
        checkoutCartStream: ReceiveChannel<List<ItemWithQuantity>>,
        startCheckoutActivity: () -> Unit
    ) {
        val alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item)

        val rootView = (alertMenuItem.actionView as FrameLayout).apply {
            setOnClickListener { startCheckoutActivity() }
        }
        val redCircle: FrameLayout = rootView.findViewById(R.id.view_alert_red_circle)

        val countTextView: TextView = rootView.findViewById(R.id.view_alert_count_textview)

        suspend fun updateItemCount(itemsInCart: List<ItemWithQuantity>) {
            withContext(Dispatchers.Main) {
                val itemsInCartCount = itemsInCart.size
                if (itemsInCartCount == 0) {
                    redCircle.visibility = View.GONE
                } else {
                    var total = 0
                    itemsInCart.forEach {
                        total += it.quantity
                    }
                    redCircle.visibility = View.VISIBLE
                    countTextView.text = total.toString()
                }
            }
        }

        launch {
            checkoutCartStream
                .consumeEach { itemsInCart ->
                    updateItemCount(itemsInCart)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.logged_in_menu, menu)
        initLoggedInMenuUi(menu, checkoutCart.itemsInCartStream()) {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
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


}
