package com.handstandsam.shoppingapp.features.itemdetail

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.di.AppComponent

class ItemDetailActivity : LoggedInActivity() {

    lateinit internal var addToCartButton: AppCompatButton

    lateinit internal var imageView: AppCompatImageView

    lateinit internal var titleText: TextView

    lateinit private var view: ItemDetailView

    lateinit private var presenter: ItemDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        titleText = findViewById(R.id.title_text)
        imageView = findViewById(R.id.image)
        addToCartButton = findViewById(R.id.add_to_cart)
        (application as MyAbstractApplication).appComponent.inject(this)

        view = MyItemDetailView()
        presenter = ItemDetailPresenter(view)
    }

    interface ItemDetailView {

        val context: Context

        fun setActionBarTitle(title: String)

        val appComponent: AppComponent

        fun setLabel(label: String)

        fun setImageUrl(imageUrl: String)

        fun showToast(message: String)
    }

    inner class MyItemDetailView : ItemDetailView {
        init {
            addToCartButton.setOnClickListener { presenter!!.addToCardClicked() }
        }

        override val context: Context
            get() = this@ItemDetailActivity.applicationContext

        override fun setActionBarTitle(title: String) {
            supportActionBar!!.title = title
        }

        override val appComponent: AppComponent
            get() = (application as MyAbstractApplication).appComponent

        override fun setLabel(label: String) {
            titleText.text = label
        }

        override fun setImageUrl(imageUrl: String) {
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }

        override fun showToast(message: String) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(intent)
    }
}
