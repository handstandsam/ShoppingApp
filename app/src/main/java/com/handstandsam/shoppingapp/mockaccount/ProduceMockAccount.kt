package com.handstandsam.shoppingapp.mockaccount


import com.handstandsam.shoppingapp.models.*
import java.util.*

class ProduceMockAccount : MockAccount() {

    internal var user: User = User("Sam", "Edwards")

    internal var categories: List<Category> = ArrayList()

    init {
        val fruitCategory = CategoryBuilder("Fruits").image(IMAGE_BASE_URL + "fuji-apple.jpg").build()
        val fruitItems = ArrayList<Item>()
        fruitItems.add(ItemBuilder("Granny Smith Apple").image(IMAGE_BASE_URL + "granny-smith-apple.jpg").build())
        fruitItems.add(ItemBuilder("Gala Apple").image(IMAGE_BASE_URL + "gala-apple.jpg").build())
        fruitItems.add(ItemBuilder("Pineapple").image(IMAGE_BASE_URL + "pineapple.jpg").build())
        fruitItems.add(ItemBuilder("Red Delicious Apple").image(IMAGE_BASE_URL + "red-delicious-apple.jpg").build())
        fruitItems.add(ItemBuilder("Fuji Apple").image(IMAGE_BASE_URL + "fuji-apple.jpg").build())
        fruitItems.add(ItemBuilder("Lemon").image(IMAGE_BASE_URL + "lemons.jpg").build())
        fruitItems.add(ItemBuilder("Grapefruit").image(IMAGE_BASE_URL + "grapefruit.jpg").build())
        fruitItems.add(ItemBuilder("Lime").image(IMAGE_BASE_URL + "lime.jpg").build())
        fruitItems.add(ItemBuilder("Orange").image(IMAGE_BASE_URL + "orange.jpg").build())
        itemByCategoryMap.put(fruitCategory.label, fruitItems)

        val floralCategory = CategoryBuilder("Floral").image(IMAGE_BASE_URL + "white-rose.jpg").build()
        val floralItems = ArrayList<Item>()
        floralItems.add(ItemBuilder("White Rose").image(IMAGE_BASE_URL + "white-rose.jpg").build())
        floralItems.add(ItemBuilder("Sunflower").image(IMAGE_BASE_URL + "sunflower.jpg").build())
        itemByCategoryMap.put(floralCategory.label, floralItems)

        val seafoodCategory = CategoryBuilder("Seafood").image(IMAGE_BASE_URL + "shrimp.jpg").build()
        val seafoodItems = ArrayList<Item>()
        seafoodItems.add(ItemBuilder("Lobster Tail").image(IMAGE_BASE_URL + "lobster-tail.jpg").build())
        seafoodItems.add(ItemBuilder("Shrimp").image(IMAGE_BASE_URL + "shrimp.jpg").build())
        seafoodItems.add(ItemBuilder("Scallop").image(IMAGE_BASE_URL + "scallops.jpg").build())
        itemByCategoryMap.put(seafoodCategory.label, seafoodItems)

        val vegetableCategory = CategoryBuilder("Vegetables").image(IMAGE_BASE_URL + "kale.jpg").build()
        val vegetableItems = ArrayList<Item>()
        vegetableItems.add(ItemBuilder("Carrot").image(IMAGE_BASE_URL + "carrots.jpg").build())
        vegetableItems.add(ItemBuilder("Cucumber").image(IMAGE_BASE_URL + "cucumber.jpg").build())
        vegetableItems.add(ItemBuilder("Kale").image(IMAGE_BASE_URL + "kale.jpg").build())
        vegetableItems.add(ItemBuilder("Romaine Lettuce").image(IMAGE_BASE_URL + "romaine-lettuce.jpg").build())
        vegetableItems.add(ItemBuilder("Artichoke").image(IMAGE_BASE_URL + "artichoke.jpg").build())
        vegetableItems.add(ItemBuilder("Beet").image(IMAGE_BASE_URL + "beet.jpg").build())
        vegetableItems.add(ItemBuilder("Radish").image(IMAGE_BASE_URL + "radish.jpg").build())
        vegetableItems.add(ItemBuilder("Tomato").image(IMAGE_BASE_URL + "tomato.jpg").build())
        vegetableItems.add(ItemBuilder("Broccoli").image(IMAGE_BASE_URL + "broccoli.jpg").build())
        vegetableItems.add(ItemBuilder("Avocado").image(IMAGE_BASE_URL + "avocado.jpg").build())
        vegetableItems.add(ItemBuilder("Red Bell Pepper").image(IMAGE_BASE_URL + "red-bell-pepper.jpg").build())
        vegetableItems.add(ItemBuilder("Orange Bell Pepper").image(IMAGE_BASE_URL + "orange-bell-pepper.jpg").build())
        vegetableItems.add(ItemBuilder("Yellow Bell Pepper").image(IMAGE_BASE_URL + "yellow-bell-pepper.jpg").build())
        itemByCategoryMap.put(vegetableCategory.label, vegetableItems)

        categories = Arrays.asList(fruitCategory, vegetableCategory, seafoodCategory, floralCategory)
    }


    override fun getUsername(): String {
        return "produce"
    }

    override fun getUser(): User {
        return user
    }

    override fun getCategories(): List<Category> {
        return categories
    }

    override fun getItemsForCategory(categoryLabel: String): MutableList<Item>? {
        return itemByCategoryMap[categoryLabel]
    }

    companion object {

        val IMAGE_BASE_URL = "https://s3.amazonaws.com/shopping-app/produce/images/"
    }
}
