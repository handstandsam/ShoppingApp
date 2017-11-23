package com.handstandsam.shoppingapp.wiremock

import android.content.Context

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.SingleRootFileSource
import com.github.tomakehurst.wiremock.core.WireMockApp

import java.io.File
import java.util.ArrayList

import timber.log.Timber

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.any
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

class WireMockManager private constructor(contextForAssets: Context, var httpPort: Int) {

    private val WILDCARD = ".*"
    private var remoteBaseUrl: String? = null

    private val fileUtils: FileUtils

    private val rootDirectory: String
        get() {
            val rootDirectory = dataDirectory + "/" + contextForAssets.packageName + "/" + wireMockDirectory
            Timber.d("rootDirectory " + rootDirectory)
            return rootDirectory
        }

    private val mappingDirectory: String
        get() {
            val mappingDirectory = rootDirectory + "/" + WireMockApp.MAPPINGS_ROOT
            Timber.d("mappingDirectory " + mappingDirectory)
            return mappingDirectory
        }

    private val fileDirectory: String
        get() {
            val fileDirectory = rootDirectory + "/" + WireMockApp.FILES_ROOT
            Timber.d("fileDirectory " + fileDirectory)
            return fileDirectory
        }

    class Builder(private val contextForAssets: Context, private val httpPort: Int) {

        fun build(): WireMockManager {
            return WireMockManager(contextForAssets, httpPort)
        }
    }

    init {
        this.contextForAssets = contextForAssets
        this.fileUtils = FileUtils(contextForAssets)
    }

    fun startProxyAndRecord(remoteBaseUrl: String) {
        if (wireMockServer != null && wireMockServer!!.isRunning) {
            wireMockServer!!.stop()
        }

        this.remoteBaseUrl = remoteBaseUrl
        removeOldWireMockStubs()
        createWireMockFolderStructure()
        instantiateProxyServer()
        wireMockServer!!.start()
    }

    fun startPlayBack() {
        if (wireMockServer != null && wireMockServer!!.isRunning) {
            wireMockServer!!.stop()
        }

        removeOldWireMockStubs()
        createWireMockFolderStructure()
        copyNewWireMockStubs()
        instantiatePlayBackServer()
        wireMockServer!!.start()
    }

    fun stopServer() {
        if (wireMockServer!!.isRunning) {
            wireMockServer!!.stop()
        }
    }

    private fun instantiateProxyServer() {
        wireMockServer = WireMockServer(wireMockConfig().port(httpPort)
                .withRootDirectory(rootDirectory))
        wireMockServer!!.enableRecordMappings(SingleRootFileSource(mappingDirectory), SingleRootFileSource(fileDirectory))
        wireMockServer!!.stubFor(any(urlMatching(WILDCARD)).willReturn(aResponse().proxiedFrom(remoteBaseUrl)))
    }

    private fun instantiatePlayBackServer() {
        wireMockServer = WireMockServer(wireMockConfig().port(httpPort)
                .withRootDirectory(rootDirectory))
    }

    private fun removeOldWireMockStubs() {
        fileUtils.deleteRecursive(File(rootDirectory))
    }

    private fun createWireMockFolderStructure() {
        val wireMockFolderPaths = object : ArrayList<String>() {
            init {
                add(rootDirectory)
                add(mappingDirectory)
                add(fileDirectory)
            }
        }
        for (folderPath in wireMockFolderPaths) {
            val dir = File(folderPath)
            if (!dir.exists()) {
                dir.mkdir()
            }
        }
    }

    private fun copyNewWireMockStubs() {
        fileUtils.copyFileOrDir(wireMockDirectory)
    }

    companion object {

        private val dataDirectory = "/data/data"
        private val wireMockDirectory = "wiremock"


        private var wireMockServer: WireMockServer? = null

        private val contextForAssets: Context
    }


}