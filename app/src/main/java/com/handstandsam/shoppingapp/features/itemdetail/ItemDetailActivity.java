package com.handstandsam.shoppingapp.features.itemdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handstandsam.shoppingapp.LoggedInActivity;
import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.di.AppComponent;

public class ItemDetailActivity extends LoggedInActivity {

    AppCompatButton addToCartButton;

    AppCompatImageView imageView;

    TextView titleText;

    private ItemDetailView view;

    private ItemDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleText = findViewById(R.id.title_text);
        imageView = findViewById(R.id.image);
        addToCartButton = findViewById(R.id.add_to_cart);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        view = new MyItemDetailView();
        presenter = new ItemDetailPresenter(view);
    }

    public interface ItemDetailView {

        Context getContext();

        void setActionBarTitle(String title);

        AppComponent getAppComponent();

        void setLabel(String label);

        void setImageUrl(String imageUrl);

        void showToast(String message);
    }

    public class MyItemDetailView implements ItemDetailView {

        public MyItemDetailView() {
            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.addToCardClicked();
                }
            });
        }

        @Override
        public Context getContext() {
            return ItemDetailActivity.this.getApplicationContext();
        }

        @Override
        public void setActionBarTitle(String title) {
            getSupportActionBar().setTitle(title);
        }

        @Override
        public AppComponent getAppComponent() {
            return ((MyAbstractApplication) getApplication()).getAppComponent();
        }

        @Override
        public void setLabel(String label) {
            titleText.setText(label);
        }

        @Override
        public void setImageUrl(String imageUrl) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }

        @Override
        public void showToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
