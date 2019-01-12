package com.handstandsam.shoppingapp.mockdata


import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.User
import java.util.*

class VideoGameMockAccount : MockAccount() {

    private val imageBaseUrl = "http://shopping-app.s3.amazonaws.com" + "/video-games/images/"

    internal val user: User = User("Sam", "Edwards")

    internal val categories: MutableList<Category> = ArrayList()

    init {
        run {
            val nesCategory = Category(
                "Nintendo",
                imageUrl("console-nes.jpg"),
                "https://en.wikipedia.org/wiki/Nintendo_Entertainment_System"
            )
            val nesGames = ArrayList<Item>()
            nesGames.add(
                Item(
                    "Super Mario Bros. 3",
                    imageUrl("nes-mario3.png"),
                    "https://en.wikipedia.org/wiki/Super_Mario_Bros._3"
                )
            )
            nesGames.add(
                Item(
                    "Tetris",
                    imageUrl("nes-tetris.jpg"),
                    "https://en.wikipedia.org/wiki/Tetris"
                )
            )
            nesGames.add(
                Item(
                    "Duck Hunt",
                    imageUrl("nes-duckhunt.jpg"),
                    "https://en.wikipedia.org/wiki/Duck_Hunt"
                )
            )
            nesGames.add(
                Item(
                    "Punch-Out!!",
                    imageUrl("nes-punchout.jpg"),
                    "https://en.wikipedia.org/wiki/Punch-Out!!_(NES)"
                )
            )


            itemsByCategory[nesCategory.label] = nesGames
            categories.add(nesCategory)
        }
        run {
            val segaCategory = Category(
                "Sega",
                imageUrl("console-sega.jpg"),
                "https://en.wikipedia.org/wiki/Sega_Genesis"
            )
            val segaGames = ArrayList<Item>()
            segaGames.add(
                Item(
                    "Sonic the Hedgehog",
                    imageUrl("sega-sonic.jpg"),
                    "https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)"
                )
            )
            segaGames.add(
                Item(
                    "Mortal Kombat",
                    imageUrl("sega-mortalkombat.jpg"),
                    "https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)"
                )
            )
            segaGames.add(
                Item(
                    "Altered Beast",
                    imageUrl("sega-alteredbeast.jpg"),
                    "https://en.wikipedia.org/wiki/Altered_Beast"
                )
            )


            itemsByCategory[segaCategory.label] = segaGames
            categories.add(segaCategory)
        }
        run {
            val category = Category(
                "Atari",
                imageBaseUrl + "console-atari.jpg",
                "https://en.wikipedia.org/wiki/Atari"
            )
            val games = ArrayList<Item>()
            games.add(
                Item(
                    "Breakout",
                    imageUrl("atari-breakout.jpg"),
                    "https://en.wikipedia.org/wiki/Breakout_(video_game)"
                )
            )
            games.add(Item("Asteroids", imageUrl("atari-asteroids.jpg")))
            games.add(
                Item(
                    "Pac Man",
                    imageUrl("atari-pacman.jpg"),
                    "https://en.wikipedia.org/wiki/Pac-Man_(Atari_2600)"
                )
            )
            games.add(
                Item(
                    "Galaxian",
                    imageUrl("atari-galaxian.jpg"),
                    "https://en.wikipedia.org/wiki/Pac-Man_(Atari_2600)"
                )
            )
            
            itemsByCategory[category.label] = games
            categories.add(category)
        }
    }

    private fun imageUrl(suffix: String): String {
        return imageBaseUrl + suffix
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
}
