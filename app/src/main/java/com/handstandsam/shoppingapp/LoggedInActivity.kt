package com.handstandsam.shoppingapp


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.features.checkout.CheckoutActivity
import com.handstandsam.shoppingapp.features.login.LoginActivity
import com.handstandsam.shoppingapp.repository.SessionManager

open class LoggedInActivity : AppCompatActivity() {

    protected val appGraph: AppGraph get() = application.graph()

    private val sessionManager: SessionManager get() = appGraph.sessionGraph.sessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!sessionManager.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logged_in_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
            R.id.view_cart -> {
                startActivity(Intent(this, CheckoutActivity::class.java))
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
