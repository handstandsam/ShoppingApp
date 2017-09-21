package com.handstandsam.shoppingapp.mockaccount;


import com.handstandsam.shoppingapp.models.Category;
import com.handstandsam.shoppingapp.models.CategoryBuilder;
import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.models.ItemBuilder;
import com.handstandsam.shoppingapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class VideoGameMockAccount extends MockAccount {

    String imageBaseUrl = "http://shopping-app.s3.amazonaws.com" + "/video-games/images/";

    User user;

    List<Category> categories = new ArrayList<>();

    public VideoGameMockAccount() {
        user = new User("Sam", "Edwards");
        {
            Category nesCategory = new CategoryBuilder("Nintendo").image(imageUrl("console-nes.jpg"))
                    .link("https://en.wikipedia.org/wiki/Nintendo_Entertainment_System").build();
            List<Item> nesGames = new ArrayList<>();
            nesGames.add(new ItemBuilder("Super Mario Bros. 3")
                    .image(imageUrl("nes-mario3.png"))
                    .link("https://en.wikipedia.org/wiki/Super_Mario_Bros._3").build());
            nesGames.add(new ItemBuilder("Tetris")
                    .image(imageUrl("nes-tetris.jpg"))
                    .link("https://en.wikipedia.org/wiki/Tetris").build());
            nesGames.add(new ItemBuilder("Duck Hunt")
                    .image(imageUrl("nes-duckhunt.jpg"))
                    .link("https://en.wikipedia.org/wiki/Duck_Hunt").build());
            nesGames.add(new ItemBuilder("Punch-Out!!")
                    .image(imageUrl("nes-punchout.jpg"))
                    .link("https://en.wikipedia.org/wiki/Punch-Out!!_(NES)").build());


            itemByCategoryMap.put(nesCategory.getLabel(), nesGames);
            categories.add(nesCategory);
        }
        {
            Category segaCategory = new CategoryBuilder("Sega")
                    .image(imageUrl("console-sega.jpg"))
                    .link("https://en.wikipedia.org/wiki/Sega_Genesis").build();
            List<Item> segaGames = new ArrayList<>();
            segaGames.add(new ItemBuilder("Sonic the Hedgehog")
                    .image(imageUrl("sega-sonic.jpg"))
                    .link("https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)").build());
            segaGames.add(new ItemBuilder("Mortal Kombat")
                    .image(imageUrl("sega-mortalkombat.jpg"))
                    .link("https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)").build());
            segaGames.add(new ItemBuilder("Altered Beast")
                    .image(imageUrl("sega-alteredbeast.jpg"))
                    .link("https://en.wikipedia.org/wiki/Altered_Beast").build());


            itemByCategoryMap.put(segaCategory.getLabel(), segaGames);
            categories.add(segaCategory);
        }
        {
            Category category = new CategoryBuilder("Atari")
                    .image(imageBaseUrl + "console-atari.jpg")
                    .link("https://en.wikipedia.org/wiki/Atari").build();
            List<Item> games = new ArrayList<>();
            games.add(new ItemBuilder("Breakout")
                    .image(imageUrl("atari-breakout.jpg"))
                    .link("https://en.wikipedia.org/wiki/Breakout_(video_game)").build());
            games.add(new ItemBuilder("Asteroids")
                    .image(imageUrl("atari-asteroids.jpg")).build());
            games.add(new ItemBuilder("Pac Man")
                    .image(imageUrl("atari-pacman.jpg"))
                    .link("https://en.wikipedia.org/wiki/Pac-Man_(Atari_2600)").build());
            games.add(new ItemBuilder("Galaxian")
                    .image(imageUrl("atari-galaxian.jpg"))
                    .link("https://en.wikipedia.org/wiki/Pac-Man_(Atari_2600)").build());


            itemByCategoryMap.put(category.getLabel(), games);
            categories.add(category);
        }
    }

    private String imageUrl(String suffix) {
        return imageBaseUrl + suffix;
    }

    @Override
    public String getUsername() {
        return "produce";
    }

    @Override
    public User getUser() {
        return user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Item> getItemsForCategory(String categoryLabel) {
        return itemByCategoryMap.get(categoryLabel);
    }
}
