package com.handstandsam.shoppingapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.SessionGraph
import com.handstandsam.shoppingapp.features.checkout.CheckoutActivity
import com.handstandsam.shoppingapp.features.login.LoginActivity
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


open class LoggedInActivity : AppCompatActivity() {

    protected val graph: AppGraph get() = application.graph()

    private val sessionGraph: SessionGraph get() = graph.sessionGraph

    private val sessionManager: SessionManager get() = sessionGraph.sessionManager

    private val checkoutCart: ShoppingCart get() = sessionGraph.shoppingCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!sessionManager.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    @Composable
    fun ShoppingCartIconWithCount(itemCount: Int, onClick: () -> Unit) {
        Box(modifier = Modifier.wrapContentSize()) {
            IconButton(onClick = {
                onClick()
            }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping Cart"
                )
            }
            if (itemCount > 0) {
                val topCornerPadding = 6.dp
                val circleWidth = 15.dp
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopEnd)
                        .padding(
                            end = topCornerPadding,
                            top = topCornerPadding
                        )
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(circleWidth),
                        onDraw = {
                            drawCircle(color = Color.Red)
                        }
                    )
                    Text(
                        modifier = Modifier
                            .size(circleWidth),
                        text = "$itemCount",
                        fontSize = 10.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
