package com.handstandsam.maintainableespresso.repository;

import com.handstandsam.maintainableespresso.models.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class ItemRepository {

    Map<String, List<Item>> itemMap = new HashMap<>();

    public ItemRepository() {

    }

    public void save(String categoryLabel, List<Item> items) {
        Timber.d("Saving Items: "+ items);
        synchronized (itemMap) {
            itemMap.put(categoryLabel, items);
        }
    }

    public List<Item> getItemsForCategory(String categoryLabel) {
        return itemMap.get(categoryLabel);
    }
}
