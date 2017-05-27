package com.handstandsam.shoppingapp.features.checkout;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.handstandsam.shoppingapp.LoggedInActivity;
import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.di.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends LoggedInActivity {

    @BindView(R.id.item_count)
    TextView itemCountTextView;

    @BindView(R.id.items)
    TextView itemsText;

    private CheckoutView view;

    private CheckoutPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        view = new MyCheckoutView();
        presenter = new CheckoutPresenter(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkout_menu, menu);
        return true;
    }

    public interface CheckoutView {

        AppComponent getAppComponent();

        void setItemCountText(String text);

        void setItemsText(String text);
    }

    public class MyCheckoutView implements CheckoutView {

        public MyCheckoutView() {
        }

        @Override
        public AppComponent getAppComponent() {
            return ((MyAbstractApplication) getApplication()).getAppComponent();
        }

        @Override
        public void setItemCountText(String text) {
            itemCountTextView.setText(text);
        }

        @Override
        public void setItemsText(String text) {
            itemsText.setText(text);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
