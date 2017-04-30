package com.handstandsam.maintainableespresso.mockaccount;


import android.content.Context;
import android.os.StrictMode;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.repository.CategoryRepository;
import com.handstandsam.maintainableespresso.repository.ItemRepository;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class Stubberator {

    @Inject
    ItemRepository itemRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    SessionManager sessionManager;

    WireMockServer wireMockServer = new WireMockServer();

    Context applicationContext;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Stubberator(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        WireMock.configureFor("10.0.2.2", 8080);
        WireMock.reset();

        wireMockServer.start();
        wireMockServer.setGlobalFixedDelay(500);
        wireMockServer.resetMappings();

        Timber.d("new Stubberator()");
        this.applicationContext = context.getApplicationContext();
        ((MyAbstractApplication) applicationContext).getAppComponent().inject(this);
    }

    public StubMapping stubFor(MappingBuilder mappingBuilder) {
        WireMock.stubFor(mappingBuilder);
        return wireMockServer.stubFor(mappingBuilder);
    }

    public void stubItAll(MockAccount mockAccount) {
        stubCategories(mockAccount);
        stubLogin(mockAccount);
        stubItems(mockAccount);
    }

    public void stubCategories(MockAccount mockAccount) {
        String json = gson.toJson(mockAccount.getCategories());
        stubFor(StubMappings.getCategories().willReturn(WireMock.aResponse().withStatus(200).withBody(json)));
    }

    public void stubItems(MockAccount mockAccount) {
        Map<String, List<Item>> itemsByCategory = mockAccount.getItemsByCategory();
        for (Map.Entry<String, List<Item>> entry : itemsByCategory.entrySet()) {
            String categoryId = entry.getKey();
            List<Item> items = entry.getValue();
            stubFor(StubMappings.getItemsForCategory(categoryId).willReturn(WireMock.aResponse().withStatus(200).withBody(gson.toJson(items))));
        }
    }

    public void stubLogin(MockAccount mockAccount) {
        stubFor(StubMappings.login().willReturn(WireMock.aResponse()
                .withStatus(200).withBody(gson.toJson(mockAccount.getUser()))));
    }

}
