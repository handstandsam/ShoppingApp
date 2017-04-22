package com.handstandsam.maintainableespresso.features.checkout;

import android.os.Bundle;
import android.widget.TextView;

import com.handstandsam.maintainableespresso.LoggedInActivity;
import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.di.AppComponent;

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
        getSupportActionBar().setTitle("Checkout Cart");
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        view = new MyCheckoutView();
        presenter = new CheckoutPresenter(view);
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
