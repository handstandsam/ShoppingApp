package com.handstandsam.shoppingapp.features.checkout;

import android.support.annotation.NonNull;

import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.repository.CheckoutCart;
import com.handstandsam.shoppingapp.repository.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void onResume() {
        List<Item> items = cart.getItems();

        String itemsCountText = items.size() + " item(s) in your cart.";

        view.setItemCountText(itemsCountText);

        Map<String, List<Item>> itemsMap = new HashMap<>();
        for (Item item : items) {
            final String key = item.getLabel();
            List<Item> list = itemsMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(item);
            itemsMap.put(key, list);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Item>> itemsEntry : itemsMap.entrySet()) {
            String key = itemsEntry.getKey();
            List<Item> value = itemsEntry.getValue();
            sb.append("* " + value.size() + " " + key + "\n");
        }

        view.setItemsText(sb.toString());
    }

}
