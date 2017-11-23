package com.handstandsam.shoppingapp.debug


import android.content.Context
import android.net.Uri
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DebugDispatcher(internal var context: Context) : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        val uri = Uri.parse(request.path)
        val username = uri.lastPathSegment

        val assetResponseJson = getAsset("user_" + username.toLowerCase() + ".json")
        return if (assetResponseJson != null && !assetResponseJson.isEmpty()) {
            MockResponse().setBody(assetResponseJson).setResponseCode(200)
        } else {
            MockResponse().setResponseCode(404)
        }
    }

    private fun getAsset(filename: String): String {
        val total = StringBuilder()
        try {
            val inputStream = context.assets.open(filename)
            val r = BufferedReader(InputStreamReader(inputStream))
            var line: String = r.readLine()
            while (line != null) {
                total.append(line).append('\n')
                line = r.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return total.toString()
    }
}
