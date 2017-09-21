package com.handstandsam.shoppingapp.mockaccount;


import android.content.Context;
import android.os.StrictMode;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.di.NetworkModule;
import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.models.User;
import com.handstandsam.shoppingapp.repository.CategoryRepository;
import com.handstandsam.shoppingapp.repository.ItemRepository;
import com.handstandsam.shoppingapp.repository.SessionManager;
import com.squareup.moshi.Moshi;

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

    Moshi moshi = new Moshi.Builder().build();

    WireMockServer wireMockServer;

    Context applicationContext;

    public Stubberator(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (NetworkModule.USE_LOCAL_SERVER) {
            wireMockServer = new WireMockServer();
            wireMockServer.start();
            wireMockServer.resetMappings();
        } else {
            WireMock.configureFor(NetworkModule.REMOTE_EMULATOR_ENDPOINT_HOST, NetworkModule.REMOTE_PORT);
            WireMock.reset();
        }

        Timber.d("new Stubberator()");
        this.applicationContext = context.getApplicationContext();
        ((MyAbstractApplication) applicationContext).getAppComponent().inject(this);
    }

    public StubMapping stubFor(MappingBuilder mappingBuilder) {
        if (NetworkModule.USE_LOCAL_SERVER) {
            return wireMockServer.stubFor(mappingBuilder);
        } else {
            return WireMock.stubFor(mappingBuilder);
        }
    }

    public void stubItAll(MockAccount mockAccount) {
        stubCategories(mockAccount);
        stubLogin(mockAccount);
        stubItems(mockAccount);
    }

    public void stubCategories(MockAccount mockAccount) {
        String json = moshi.adapter(List.class).toJson(mockAccount.getCategories());
        stubFor(StubMappings.getCategories().willReturn(WireMock.aResponse().withStatus(200).withBody(json)));
    }

    public void stubItems(MockAccount mockAccount) {
        Map<String, List<Item>> itemsByCategory = mockAccount.getItemsByCategory();
        for (Map.Entry<String, List<Item>> entry : itemsByCategory.entrySet()) {
            String categoryId = entry.getKey();
            List<Item> items = entry.getValue();
            String json = moshi.adapter(List.class).toJson(items);
            stubFor(StubMappings.getItemsForCategory(categoryId).willReturn(WireMock.aResponse().withStatus(200).withBody(json)));
        }
    }

    public void stubLogin(MockAccount mockAccount) {
        String json = moshi.adapter(User.class).toJson(mockAccount.getUser());
        stubFor(StubMappings.login().willReturn(WireMock.aResponse()
                .withStatus(200).withBody(json)));
    }

}
