package com.handstandsam.shoppingapp.mockaccount;


import com.handstandsam.shoppingapp.models.Category;
import com.handstandsam.shoppingapp.models.CategoryBuilder;
import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.models.ItemBuilder;
import com.handstandsam.shoppingapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoGameMockAccount extends MockAccount {

    User user;

    List<Category> categories = new ArrayList<>();

    public VideoGameMockAccount() {
        user = new User("Sam", "Edwards");

        Category nesCategory = new CategoryBuilder("Nintendo").image("https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/NES-Console-Set.jpg/440px-NES-Console-Set.jpg").link("https://en.wikipedia.org/wiki/Nintendo_Entertainment_System").build();
        List<Item> nesGames = new ArrayList<>();
        nesGames.add(new ItemBuilder("Super Mario Bros. 3")
                .image("https://upload.wikimedia.org/wikipedia/en/a/a5/Super_Mario_Bros._3_coverart.png")
                .link("https://en.wikipedia.org/wiki/Super_Mario_Bros._3").build());
        nesGames.add(new ItemBuilder("Tetris")
                .image("https://upload.wikimedia.org/wikipedia/en/8/8d/NES_Tetris_Box_Front.jpg")
                .link("https://en.wikipedia.org/wiki/Tetris").build());
        nesGames.add(new ItemBuilder("Duck Hunt")
                .image("https://upload.wikimedia.org/wikipedia/en/1/14/DuckHuntBox.jpg")
                .link("https://en.wikipedia.org/wiki/Duck_Hunt").build());
        nesGames.add(new ItemBuilder("Punch-Out!!")
                .image("https://upload.wikimedia.org/wikipedia/en/3/3d/Punch-out_mrdream_boxart.PNG")
                .link("https://en.wikipedia.org/wiki/Punch-Out!!_(NES)").build());



        itemByCategoryMap.put(nesCategory.getLabel(), nesGames);

        Category segaCategory = new CategoryBuilder("Sega")
                .image("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Sega-Genesis-Mk2-6button.jpg/500px-Sega-Genesis-Mk2-6button.jpg")
                .link("https://en.wikipedia.org/wiki/Sega_Genesis").build();
        List<Item> segaGames = new ArrayList<>();
        segaGames.add(new ItemBuilder("Sonic the Hedgehog")
                .image("https://upload.wikimedia.org/wikipedia/en/b/ba/Sonic_the_Hedgehog_1_Genesis_box_art.jpg")
                .link("https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)").build());
        segaGames.add(new ItemBuilder("Mortal Kombat")
                .image("https://upload.wikimedia.org/wikipedia/en/3/33/Mortal_Kombat_cover.JPG")
                .link("https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)").build());
        segaGames.add(new ItemBuilder("Altered Beast")
                .image("https://upload.wikimedia.org/wikipedia/en/0/05/Altered_Beast_cover.jpg")
                .link("https://en.wikipedia.org/wiki/Altered_Beast").build());


        itemByCategoryMap.put(segaCategory.getLabel(), segaGames);

        categories = Arrays.asList(nesCategory, segaCategory);
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
