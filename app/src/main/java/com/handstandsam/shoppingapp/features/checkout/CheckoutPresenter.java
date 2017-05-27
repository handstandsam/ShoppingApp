package com.handstandsam.shoppingapp.features.checkout;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.repository.CheckoutCart;
import com.handstandsam.shoppingapp.repository.SessionManager;

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
        sb.append(items.size() + " item(s) in your cart.\n");
        for (Item item : items) {
            sb.append("* " + item.getLabel() + ".\n");
        }

        view.setText(sb.toString());
    }

}
