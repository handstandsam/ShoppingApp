package com.handstandsam.shoppingapp.mockaccount;


import com.handstandsam.shoppingapp.models.Category;
import com.handstandsam.shoppingapp.models.CategoryBuilder;
import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.models.ItemBuilder;
import com.handstandsam.shoppingapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class AndroidLibsMockAccount extends MockAccount {

    public static final String IMAGE_BASE_URL = "https://s3.amazonaws.com/shopping-app/android-libs/images/";

    User user;

    List<Category> categories = new ArrayList<>();

    public AndroidLibsMockAccount() {
        user = new User("Sam", "Edwards");
        {
            Category category = new CategoryBuilder("HTTP Mocking").image("https://www.kullabs.com/uploads/Communication11.jpg").link("https://www.kullabs.com/classes/subjects/units/lessons/notes/note-detail/6823").build();
            List<Item> items = new ArrayList<>();
            items.add(new ItemBuilder("WireMock")
                    .image(IMAGE_BASE_URL + "wiremock.png")
                    .link("https://github.com/tomakehurst/wiremock").build());
            items.add(new ItemBuilder("Mock Web Server")
                    .image(IMAGE_BASE_URL + "mockwebserver.png")
                    .link("https://github.com/square/okhttp/tree/master/mockwebserver").build());
            items.add(new ItemBuilder("RESTMock")
                    .image(IMAGE_BASE_URL + "restmock.png")
                    .link("https://github.com/andrzejchm/RESTMock").build());
            items.add(new ItemBuilder("OkReplay")
                    .image(IMAGE_BASE_URL + "okreplay.png")
                    .link("https://github.com/airbnb/okreplay").build());
            items.add(new ItemBuilder("Mockito")
                    .image(IMAGE_BASE_URL + "mockito.png")
                    .link("https://github.com/mockito/mockito").build());

            itemByCategoryMap.put(category.getLabel(), items);
            categories.add(category);
        }
        {
            Category category = new CategoryBuilder("UI")
                    .image("https://developer.android.com/studio/images/write/layout-editor-callouts_2-3_2x.png")
                    .link("https://developer.android.com/studio/write/layout-editor.html").build();
            List<Item> items = new ArrayList<>();
            items.add(new ItemBuilder("Butter Knife")
                    .image("http://jakewharton.github.io/butterknife/static/logo.png")
                    .link("http://jakewharton.github.io/butterknife/").build());

            itemByCategoryMap.put(category.getLabel(), items);
            categories.add(category);
        }

        {
            Category category = new CategoryBuilder("Dependency Injection")
                    .image("https://upload.wikimedia.org/wikipedia/commons/1/10/W3sDesign_Dependency_Injection_Design_Pattern_UML.jpg")
                    .link("https://en.wikipedia.org/wiki/Dependency_injection").build();
            List<Item> items = new ArrayList<>();
            items.add(new ItemBuilder("Dagger 2")
                    .image("https://upload.wikimedia.org/wikipedia/en/b/ba/Sonic_the_Hedgehog_1_Genesis_box_art.jpg")
                    .link("https://en.wikipedia.org/wiki/Sonic_the_Hedgehog_(1991_video_game)").build());
            items.add(new ItemBuilder("Toothpick")
                    .image("https://upload.wikimedia.org/wikipedia/en/3/33/Mortal_Kombat_cover.JPG")
                    .link("https://en.wikipedia.org/wiki/Mortal_Kombat_(1992_video_game)").build());
            items.add(new ItemBuilder("Altered Beast")
                    .image("https://upload.wikimedia.org/wikipedia/en/0/05/Altered_Beast_cover.jpg")
                    .link("https://en.wikipedia.org/wiki/Altered_Beast").build());


            itemByCategoryMap.put(category.getLabel(), items);
            categories.add(category);
        }

        {
            Category category = new CategoryBuilder("Images")
                    .image("https://developer.android.com/images/tools/vas-materialicon_2-2_2x.png")
                    .link("https://developer.android.com/studio/write/vector-asset-studio.html").build();
            List<Item> items = new ArrayList<>();
            items.add(new ItemBuilder("Glide")
                    .image("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                    .link("https://github.com/bumptech/glide").build());
            items.add(new ItemBuilder("Picasso")
                    .image("http://square.github.io/picasso/static/sample.png")
                    .link("https://square.github.io/picasso/").build());


            itemByCategoryMap.put(category.getLabel(), items);
            categories.add(category);
        }
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
