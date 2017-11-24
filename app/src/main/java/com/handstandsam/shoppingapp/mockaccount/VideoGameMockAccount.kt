package com.handstandsam.shoppingapp.mockaccount


import com.handstandsam.shoppingapp.models.*
import java.util.*

class VideoGameMockAccount : MockAccount() {


    private var imageBaseUrl = "http://shopping-app.s3.amazonaws.com" + "/video-games/images/"

    internal var user: User = User("Sam", "Edwards")

    internal var categories: MutableList<Category> = ArrayList()

    init {
        run {
            val nesCategory = CategoryBuilder("Nintendo").image(imageUrl("console-nes.jpg"))
                    .link("https://en.wikipedia.org/wiki/Nintendo_Entertainment_System").build()
            val nesGames = ArrayList<Item>()
            nesGames.add(ItemBuilder("Super Mario Bros. 3")
                    .image(imageUrl("nes-mario3.png"))
                    .link("https://en.wikipedia.org/wiki/Super_Mario_Bros._3").build())
            nesGames.add(ItemBuilder("Tetris")
                    .image(imageUrl("nes-tetris.jpg"))
                    .link("https://en.wikipedia.org/wiki/Tetris").build())
            nesGames.add(ItemBuilder("Duck Hunt")
                    .image(imageUrl("nes-duckhunt.jpg"))
                    .link("https://en.wikipedia.org/wiki/Duck_Hunt").build())
            nesGames.add(ItemBuilder("Punch-Out!!")
                    .image(imageUrl("nes-punchout.jpg"))
                    .link("https://en.wikipedia.org/wiki/Punch-Out!!_(NES)").build())


            itemsByCategory[nesCategory.label!!] = nesGames
            categories.add(nesCategory)
        }
        run {
            val segaCategory = CategoryBuilder("Sega")
                    .image(imageUrl("console-sega.jpg"))
                    .link("https://en.wikipedia.org/wiki/Sega_Genesis").build()
            val segaGames = ArrayList<Item>()
            segaGames.add(ItemBuilder("Sonic the Hedgehog")
                    .image(imageUrl("sega-sonic.jpg"))
                    .link("https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)").build())
            segaGames.add(ItemBuilder("Mortal Kombat")
                    .image(imageUrl("sega-mortalkombat.jpg"))
                    .link("https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)").build())
            segaGames.add(ItemBuilder("Altered Beast")
                    .image(imageUrl("sega-alteredbeast.jpg"))
                    .link("https://en.wikipedia.org/wiki/Altered_Beast").build())


            itemsByCategory[segaCategory.label!!] = segaGames
            categories.add(segaCategory)
        }
        run {
            val category = CategoryBuilder("Atari")
                    .image(imageBaseUrl + "console-atari.jpg")
                    .link("https://en.wikipedia.org/wiki/Atari").build()
            val games = ArrayList<Item>()
            games.add(ItemBuilder("Breakout")
                    .image(imageUrl("atari-breakout.jpg"))
                    .link("https://en.wikipedia.org/wiki/Breakout_(video_game)").build())
            games.add(ItemBuilder("Asteroids")
                    .image(imageUrl("atari-asteroids.jpg")).build())
            games.add(ItemBuilder("Pac Man")
                    .image(imageUrl("atari-pacman.jpg"))
                    .link("https://en.wikipedia.org/wiki/Pac-Man_(Atari_2600)").build())
            games.add(ItemBuilder("Galaxian")
                    .image(imageUrl("atari-galaxian.jpg"))
                    .link("https://en.wikipedia.org/wiki/Pac-Man_(Atari_2600)").build())


            itemsByCategory[category.label!!] = games
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

    override fun getItemsForCategory(categoryLabel: String): MutableList<Item>? {
        return itemsByCategory[categoryLabel]
    }
}
