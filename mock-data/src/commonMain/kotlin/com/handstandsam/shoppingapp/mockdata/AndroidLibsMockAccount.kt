package com.handstandsam.shoppingapp.mockdata


import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.User

class AndroidLibsMockAccount : MockAccount() {

    internal var user: User = User("Sam", "Edwards")

    internal var categories: MutableList<Category> = ArrayList()

    init {
        run {
            val category = Category(
                "HTTP Mocking",
                "https://www.kullabs.com/uploads/Communication11.jpg",
                "https://www.kullabs.com/classes/subjects/units/lessons/notes/note-detail/6823"
            )
            val items = listOf(

                Item(
                    "WireMock",
                    IMAGE_BASE_URL + "wiremock.png",
                    "https://github.com/tomakehurst/wiremock"
                ),
                Item(
                    "Mock Web Server",
                    IMAGE_BASE_URL + "mockwebserver.png",
                    "https://github.com/square/okhttp/tree/master/mockwebserver"
                ),
                Item(
                    "RESTMock",
                    IMAGE_BASE_URL + "restmock.png",
                    "https://github.com/andrzejchm/RESTMock"
                ),
                Item(
                    "OkReplay",
                    IMAGE_BASE_URL + "okreplay.png",
                    "https://github.com/airbnb/okreplay"
                ),
                Item(
                    "Mockito",
                    IMAGE_BASE_URL + "mockito.png",
                    "https://github.com/mockito/mockito"
                )
            )

            itemsByCategory[category.label] = items

            categories.add(category)
        }
        run {
            val category = Category(
                "UI",
                "https://developer.android.com/studio/images/write/layout-editor-callouts_2-3_2x.png",
                "https://developer.android.com/studio/write/layout-editor.html"
            )
            val items = ArrayList<Item>()
            items.add(
                Item(
                    "Butter Knife",
                    "http://jakewharton.github.io/butterknife/static/logo.png",
                    "http://jakewharton.github.io/butterknife/"
                )
            )

            itemsByCategory[category.label] = items
            categories.add(category)
        }

        run {
            val category = Category(
                "Dependency Injection",
                "https://upload.wikimedia.org/wikipedia/commons/1/10/W3sDesign_Dependency_Injection_Design_Pattern_UML.jpg",
                "https://en.wikipedia.org/wiki/Dependency_injection"
            )
            val items = listOf(
                Item(
                    "Dagger 2",
                    "https://upload.wikimedia.org/wikipedia/en/b/ba/Sonic_the_Hedgehog_1_Genesis_box_art.jpg",
                    "https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)"
                )
                , Item(
                    "Toothpick",
                    "https://upload.wikimedia.org/wikipedia/en/3/33/Mortal_Kombat_cover.JPG",
                    "https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)"
                )
                ,
                Item(
                    "Altered Beast",
                    "https://upload.wikimedia.org/wikipedia/en/0/05/Altered_Beast_cover.jpg",
                    "https://en.wikipedia.org/wiki/Altered_Beast"
                )
            )

            itemsByCategory[category.label] = items

            categories.add(category)
        }

        run {
            val category = Category(
                "Images",
                "https://developer.android.com/images/tools/vas-materialicon_2-2_2x.png",
                "https://developer.android.com/studio/write/vector-asset-studio.html"
            )
            val items = listOf(
                Item(
                    "Glide",
                    "https://github.com/bumptech/glide/raw/master/static/glide_logo.png",
                    "https://github.com/bumptech/glide"
                )
                ,
                Item(
                    "Picasso",
                    "http://square.github.io/picasso/static/sample.png",
                    "https://square.github.io/picasso/"
                )
            )

            itemsByCategory[category.label] = items
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

    override fun getItemsForCategory(categoryLabel: String): List<Item>? {
        return itemsByCategory[categoryLabel]
    }

    companion object {
        const val IMAGE_BASE_URL = "https://s3.amazonaws.com/shopping-app/android-libs/images/"
    }
}
