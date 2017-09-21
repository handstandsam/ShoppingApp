package com.handstandsam.shoppingapp.wiremock;

import android.content.Context;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.core.WireMockApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockManager {

    public static class Builder {
        private final Context contextForAssets;

        public Builder(Context contextForAssets) {
            this.contextForAssets = contextForAssets;
        }

        public WireMockManager build() {
            return new WireMockManager(contextForAssets);
        }
    }


    public static int HTTP_PORT = 9999;

    enum WireMockMode {
        RECORD, PLAYBACK, NONE
    }

    private static String dataDirectory = "/data/data";
    private static String wireMockDirectory = "wiremock";


    private String WILDCARD = ".*";
    private String remoteBaseUrl;

    private FileUtils fileUtils;


    private static WireMockServer wireMockServer;

    private static Context contextForAssets;

    private WireMockManager(Context contextForAssets) {
        this.contextForAssets = contextForAssets;
        this.fileUtils = new FileUtils(contextForAssets);
    }

    public void startProxyAndRecord(Context contextForAssets, String remoteBaseUrl) {
        this.contextForAssets = contextForAssets;
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }

        this.remoteBaseUrl = remoteBaseUrl;
        removeOldWireMockStubs();
        createWireMockFolderStructure();
        instantiateProxyServer();
        wireMockServer.start();
    }

    public void startPlayBack() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }

        removeOldWireMockStubs();
        createWireMockFolderStructure();
        copyNewWireMockStubs();
        instantiatePlayBackServer();
        wireMockServer.start();
    }

    public void stopServer() {
        if (wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    private void instantiateProxyServer() {
        wireMockServer = new WireMockServer(wireMockConfig().port(HTTP_PORT)
                .withRootDirectory(getRootDirectory()));
        wireMockServer.enableRecordMappings(new SingleRootFileSource(getMappingDirectory()), new SingleRootFileSource(getFileDirectory()));
        wireMockServer.stubFor(any(urlMatching(WILDCARD)).willReturn(aResponse().proxiedFrom(remoteBaseUrl)));
    }

    private void instantiatePlayBackServer() {
        wireMockServer = new WireMockServer(wireMockConfig().port(HTTP_PORT)
                .withRootDirectory(getRootDirectory()));
    }

    private void removeOldWireMockStubs() {
        fileUtils.deleteRecursive(new File(getRootDirectory()));
    }

    private void createWireMockFolderStructure() {
        List<String> wireMockFolderPaths = new ArrayList<String>() {{
            add(getRootDirectory());
            add(getMappingDirectory());
            add(getFileDirectory());
        }};
        for (String folderPath : wireMockFolderPaths) {
            File dir = new File(folderPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
    }

    private void copyNewWireMockStubs() {
        fileUtils.copyFileOrDir(wireMockDirectory);
    }

    private String getRootDirectory() {
        String rootDirectory = dataDirectory + "/" + contextForAssets.getPackageName() + "/" + wireMockDirectory;
        Timber.d("rootDirectory " + rootDirectory);
        return rootDirectory;
    }

    private String getMappingDirectory() {
        String mappingDirectory = getRootDirectory() + "/" + WireMockApp.MAPPINGS_ROOT;
        Timber.d("mappingDirectory " + mappingDirectory);
        return mappingDirectory;
    }

    private String getFileDirectory() {
        String fileDirectory = getRootDirectory() + "/" + WireMockApp.FILES_ROOT;
        Timber.d("fileDirectory " + fileDirectory);
        return fileDirectory;
    }


}