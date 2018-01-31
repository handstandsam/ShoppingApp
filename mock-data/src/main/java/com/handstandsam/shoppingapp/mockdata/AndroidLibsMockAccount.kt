package com.handstandsam.shoppingapp.mockdata


import com.handstandsam.shoppingapp.models.*
import com.handstandsam.shoppingapp.models.builders.CategoryBuilder
import com.handstandsam.shoppingapp.models.builders.ItemBuilder
import java.util.*

class AndroidLibsMockAccount : MockAccount() {

    internal var user: User = User("Sam", "Edwards")

    internal var categories: MutableList<Category> = ArrayList()

    init {
        run {
            val category = CategoryBuilder("HTTP Mocking").image("https://www.kullabs.com/uploads/Communication11.jpg").link("https://www.kullabs.com/classes/subjects/units/lessons/notes/note-detail/6823").build()
            val items = ArrayList<Item>()
            items.add(ItemBuilder("WireMock")
                    .image(IMAGE_BASE_URL + "wiremock.png")
                    .link("https://github.com/tomakehurst/wiremock").build())
            items.add(ItemBuilder("Mock Web Server")
                    .image(IMAGE_BASE_URL + "mockwebserver.png")
                    .link("https://github.com/square/okhttp/tree/master/mockwebserver").build())
            items.add(ItemBuilder("RESTMock")
                    .image(IMAGE_BASE_URL + "restmock.png")
                    .link("https://github.com/andrzejchm/RESTMock").build())
            items.add(ItemBuilder("OkReplay")
                    .image(IMAGE_BASE_URL + "okreplay.png")
                    .link("https://github.com/airbnb/okreplay").build())
            items.add(ItemBuilder("Mockito")
                    .image(IMAGE_BASE_URL + "mockito.png")
                    .link("https://github.com/mockito/mockito").build())

            itemsByCategory.put(category.label!!, items)
            categories.add(category)
        }
        run {
            val category = CategoryBuilder("UI")
                    .image("https://developer.android.com/studio/images/write/layout-editor-callouts_2-3_2x.png")
                    .link("https://developer.android.com/studio/write/layout-editor.html").build()
            val items = ArrayList<Item>()
            items.add(ItemBuilder("Butter Knife")
                    .image("http://jakewharton.github.io/butterknife/static/logo.png")
                    .link("http://jakewharton.github.io/butterknife/").build())

            itemsByCategory.put(category.label!!, items)
            categories.add(category)
        }

        run {
            val category = CategoryBuilder("Dependency Injection")
                    .image("https://upload.wikimedia.org/wikipedia/commons/1/10/W3sDesign_Dependency_Injection_Design_Pattern_UML.jpg")
                    .link("https://en.wikipedia.org/wiki/Dependency_injection").build()
            val items = ArrayList<Item>()
            items.add(ItemBuilder("Dagger 2")
                    .image("https://upload.wikimedia.org/wikipedia/en/b/ba/Sonic_the_Hedgehog_1_Genesis_box_art.jpg")
                    .link("https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)").build())
            items.add(ItemBuilder("Toothpick")
                    .image("https://upload.wikimedia.org/wikipedia/en/3/33/Mortal_Kombat_cover.JPG")
                    .link("https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)").build())
            items.add(ItemBuilder("Altered Beast")
                    .image("https://upload.wikimedia.org/wikipedia/en/0/05/Altered_Beast_cover.jpg")
                    .link("https://en.wikipedia.org/wiki/Altered_Beast").build())


            itemsByCategory.put(category.label!!, items)
            categories.add(category)
        }

        run {
            val category = CategoryBuilder("Images")
                    .image("https://developer.android.com/images/tools/vas-materialicon_2-2_2x.png")
                    .link("https://developer.android.com/studio/write/vector-asset-studio.html").build()
            val items = ArrayList<Item>()
            items.add(ItemBuilder("Glide")
                    .image("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                    .link("https://github.com/bumptech/glide").build())
            items.add(ItemBuilder("Picasso")
                    .image("http://square.github.io/picasso/static/sample.png")
                    .link("https://square.github.io/picasso/").build())

            itemsByCategory.put(category.label!!, items)
            categories.add(category)
        }
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
        return itemsByCategory[categoryLabel]
    }

    companion object {

        val IMAGE_BASE_URL = "https://s3.amazonaws.com/shopping-app/android-libs/images/"
    }
}
