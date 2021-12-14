package com.handstandsam.shoppingapp.mockdata


import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.User

class ProduceMockAccount : MockAccount() {

    internal val user: User = User("Sam", "Edwards")

    internal val categories: MutableList<Category> = mutableListOf()

    init {
        val fruitCategory = Category("Fruits", IMAGE_BASE_URL + "fuji-apple.jpg")
        val fruitItems = ArrayList<Item>()
        fruitItems.add(Item("Granny Smith Apple", IMAGE_BASE_URL + "granny-smith-apple.jpg"))
        fruitItems.add(Item("Gala Apple", IMAGE_BASE_URL + "gala-apple.jpg"))
        fruitItems.add(Item("Pineapple", IMAGE_BASE_URL + "pineapple.jpg"))
        fruitItems.add(Item("Red Delicious Apple", IMAGE_BASE_URL + "red-delicious-apple.jpg"))
        fruitItems.add(Item("Fuji Apple", IMAGE_BASE_URL + "fuji-apple.jpg"))
        fruitItems.add(Item("Lemon", IMAGE_BASE_URL + "lemons.jpg"))
        fruitItems.add(Item("Grapefruit", IMAGE_BASE_URL + "grapefruit.jpg"))
        fruitItems.add(Item("Lime", IMAGE_BASE_URL + "lime.jpg"))
        fruitItems.add(Item("Orange", IMAGE_BASE_URL + "orange.jpg"))
        itemsByCategory[fruitCategory.label] = fruitItems

        val floralCategory =
            Category("Floral", IMAGE_BASE_URL + "white-rose.jpg")
        val floralItems = ArrayList<Item>()
        floralItems.add(Item("White Rose", IMAGE_BASE_URL + "white-rose.jpg"))
        floralItems.add(Item("Sunflower", IMAGE_BASE_URL + "sunflower.jpg"))
        itemsByCategory[floralCategory.label] = floralItems

        val seafoodCategory =
            Category("Seafood", IMAGE_BASE_URL + "shrimp.jpg")
        val seafoodItems = ArrayList<Item>()
        seafoodItems.add(Item("Lobster Tail", IMAGE_BASE_URL + "lobster-tail.jpg"))
        seafoodItems.add(Item("Shrimp", IMAGE_BASE_URL + "shrimp.jpg"))
        seafoodItems.add(Item("Scallop", IMAGE_BASE_URL + "scallops.jpg"))
        itemsByCategory[seafoodCategory.label] = seafoodItems

        val vegetableCategory =
            Category("Vegetables", IMAGE_BASE_URL + "kale.jpg")
        val vegetableItems = ArrayList<Item>()
        vegetableItems.add(Item("Carrot", IMAGE_BASE_URL + "carrots.jpg"))
        vegetableItems.add(Item("Cucumber", IMAGE_BASE_URL + "cucumber.jpg"))
        vegetableItems.add(Item("Kale", IMAGE_BASE_URL + "kale.jpg"))
        vegetableItems.add(Item("Romaine Lettuce", IMAGE_BASE_URL + "romaine-lettuce.jpg"))
        vegetableItems.add(Item("Artichoke", IMAGE_BASE_URL + "artichoke.jpg"))
        vegetableItems.add(Item("Beet", IMAGE_BASE_URL + "beet.jpg"))
        vegetableItems.add(Item("Radish", IMAGE_BASE_URL + "radish.jpg"))
        vegetableItems.add(Item("Tomato", IMAGE_BASE_URL + "tomato.jpg"))
        vegetableItems.add(Item("Broccoli", IMAGE_BASE_URL + "broccoli.jpg"))
        vegetableItems.add(Item("Avocado", IMAGE_BASE_URL + "avocado.jpg"))
        vegetableItems.add(Item("Red Bell Pepper", IMAGE_BASE_URL + "red-bell-pepper.jpg"))
        vegetableItems.add(Item("Orange Bell Pepper", IMAGE_BASE_URL + "orange-bell-pepper.jpg"))
        vegetableItems.add(Item("Yellow Bell Pepper", IMAGE_BASE_URL + "yellow-bell-pepper.jpg"))
        itemsByCategory[vegetableCategory.label] = vegetableItems

        categories.addAll(
            listOf(
                fruitCategory,
                vegetableCategory,
                seafoodCategory,
                floralCategory
            )
        )
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

    override fun getItemsForCategory(categoryLabel: String): List<Item>? {
        return itemsByCategory[categoryLabel]
    }

    companion object {

        val IMAGE_BASE_URL = "https://s3.amazonaws.com/shopping-app/produce/images/"
    }
}
