package com.handstandsam.maintainableespresso.mockaccount;


import com.github.tomakehurst.wiremock.client.MappingBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class StubMappings {


    public static MappingBuilder getCategories() {
        return get(urlEqualTo("/categories"));
    }

    public static MappingBuilder getItemsForCategory(String categoryId) {
        return get(urlEqualTo("/category/" + categoryId + "/items"));
    }

    public static MappingBuilder login() {
        return post(urlEqualTo("/login"));
    }

}
