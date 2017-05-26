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

    @BindView(R.id.text)
    TextView textView;

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

        void setText(String message);
    }

    public class MyCheckoutView implements CheckoutView {

        public MyCheckoutView() {
        }

        @Override
        public AppComponent getAppComponent() {
            return ((MyAbstractApplication) getApplication()).getAppComponent();
        }

        @Override
        public void setText(String message) {
            textView.setText(message);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
