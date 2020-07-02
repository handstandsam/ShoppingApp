package com.handstandsam.shoppingapp.features.itemdetail

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.R

class ItemDetailActivity : LoggedInActivity() {

    internal lateinit var addToCartButton: AppCompatButton

    internal lateinit var imageView: AppCompatImageView

    internal lateinit var titleText: TextView

    private lateinit var view: ItemDetailView

    private lateinit var presenter: ItemDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        titleText = findViewById(R.id.title_text)
        imageView = findViewById(R.id.image)
        addToCartButton = findViewById(R.id.add_to_cart)

        view = MyItemDetailView()
        presenter = ItemDetailPresenter(
            view = view,
            cart = graph.sessionGraph.shoppingCart
        )
    }

    interface ItemDetailView {

        val context: Context

        fun setActionBarTitle(title: String)

        fun setLabel(label: String)

        fun setImageUrl(imageUrl: String)

        fun showToast(message: String)
    }

    inner class MyItemDetailView : ItemDetailView {
        init {
            addToCartButton.setOnClickListener { presenter.addToCardClicked() }
        }

        override val context: Context
            get() = this@ItemDetailActivity.applicationContext

        override fun setActionBarTitle(title: String) {
            supportActionBar!!.title = title
        }

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
