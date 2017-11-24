package com.handstandsam.shoppingapp.features.login

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatEditText
import android.view.KeyEvent
import android.view.View
import android.widget.Toast

import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.di.AppComponent
import com.handstandsam.shoppingapp.features.home.HomeActivity

import io.reactivex.disposables.Disposable

class LoginActivity : AppCompatActivity() {

    lateinit var rememberMeCheckbox: AppCompatCheckBox

    lateinit var usernameEditText: AppCompatEditText

    lateinit var passwordEditText: AppCompatEditText

    internal var disposable: Disposable? = null
    lateinit private var loginView: MyLoginView
    lateinit private var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.title = "Log in to Shopping App"
        passwordEditText = findViewById<AppCompatEditText>(R.id.password)
        usernameEditText = findViewById<AppCompatEditText>(R.id.username)
        rememberMeCheckbox = findViewById<AppCompatCheckBox>(R.id.remember_me)
        findViewById<View>(R.id.submit).setOnClickListener { presenter.loginClicked() }
        (application as MyAbstractApplication).appComponent.inject(this)

        loginView = MyLoginView()
        presenter = LoginPresenter(loginView)

        usernameEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                presenter!!.loginClicked()
                return@OnKeyListener true
            }
            false
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    interface LoginView {
        val appComponent: AppComponent

        fun startHomeActivity()

        var username: String

        val isRememberMeChecked: Boolean

        fun setRememberMe(rememberMe: Boolean)

        fun kickToHomeScreen()

        val password: String

        fun showToast(@StringRes stringResourceId: Int)
    }


    inner class MyLoginView : LoginView {

        override val appComponent: AppComponent
            get() = (application as MyAbstractApplication).appComponent

        override fun startHomeActivity() {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            this@LoginActivity.startActivity(intent)
        }

        override var username: String
            get() = usernameEditText.text.toString()
            set(username) = usernameEditText.setText(username)

        override val isRememberMeChecked: Boolean
            get() = rememberMeCheckbox.isChecked

        override fun setRememberMe(value: Boolean) {
            rememberMeCheckbox.isChecked = value
        }

        override fun kickToHomeScreen() {
            this@LoginActivity.startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }

        override val password: String
            get() = passwordEditText.text.toString()

        override fun showToast(@StringRes stringResourceId: Int) {
            Toast.makeText(applicationContext, stringResourceId, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }
}
