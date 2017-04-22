package com.handstandsam.maintainableespresso.features.checkout;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.repository.CheckoutCart;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import java.util.List;

import javax.inject.Inject;

public class CheckoutPresenter {

    private final CheckoutActivity.CheckoutView view;

    @Inject
    SessionManager sessionManager;

    @Inject
    CheckoutCart cart;

    public CheckoutPresenter(@NonNull CheckoutActivity.CheckoutView view) {
        this.view = view;
        view.getAppComponent().inject(this);
    }

    public void onResume(Intent intent) {
        List<Item> items = cart.getItems();
        StringBuilder sb = new StringBuilder();
        sb.append(items.size() + " items in your cart.\n");
        for (Item item : items) {
            sb.append("* " + item.getLabel() + ".\n");
        }

        view.setText(sb.toString());
    }

}
