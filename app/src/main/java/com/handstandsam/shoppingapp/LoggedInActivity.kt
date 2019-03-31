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
import com.handstandsam.shoppingapp.features.checkout.CheckoutActivity
import com.handstandsam.shoppingapp.features.login.LoginActivity
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


open class LoggedInActivity : AppCompatActivity(),
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    protected val graph: AppGraph get() = application.graph()

    private val sessionManager: SessionManager get() = graph.sessionGraph.sessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!sessionManager.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    lateinit var redCircle: FrameLayout
    lateinit var countTextView: TextView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.logged_in_menu, menu)
        val alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item)
        val rootView = (alertMenuItem.actionView as FrameLayout)

        redCircle = rootView.findViewById(R.id.view_alert_red_circle)
        countTextView = rootView.findViewById(R.id.view_alert_count_textview)
        rootView.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }

        launch {
            graph.sessionGraph.checkoutCart.itemsInCartStream().consumeEach { itemsInCartCount ->
                runOnUiThread {
                    if (itemsInCartCount == 0) {
                        redCircle.visibility = View.GONE
                    } else {
                        redCircle.visibility = View.VISIBLE
                        countTextView.text = itemsInCartCount.toString()
                    }
                }
            }
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
