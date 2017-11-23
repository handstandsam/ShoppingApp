package com.handstandsam.shoppingapp.wiremock

import android.content.Context
import android.util.Log
import com.github.tomakehurst.wiremock.core.WireMockApp
import timber.log.Timber
import java.io.*

class FileUtils(private val contextForAssets: Context) {

    private val packageName = contextForAssets.packageName

    fun copyFileOrDir(path: String) {
        val assets: Array<String>
        try {
            assets = contextForAssets.assets.list(path)
            Timber.v(path + " - " + assets)
            if (assets.size == 0) {
                copyFile(path)
            } else {
                var fullPath = "$dataDirectory/$packageName/$path"
                fullPath = fullPath.replace(tempDirectoryName, replacementDirectoryName)
                val directory = File(fullPath)
                if (!directory.exists()) {
                    directory.mkdir()
                }
                for (i in assets.indices) {
                    copyFileOrDir(path + "/" + assets[i])
                }
            }
        } catch (ex: IOException) {
            Log.e("tag", "I/O Exception", ex)
        }

    }

    private fun copyFile(filename: String) {
        val inputStream: InputStream
        val out: OutputStream
        try {
            //context is not defined
            inputStream = contextForAssets.assets.open(filename)
            var newFileName = "$dataDirectory/$packageName/$filename"
            newFileName = newFileName.replace(tempDirectoryName, replacementDirectoryName)
            out = FileOutputStream(newFileName)

            val buffer = ByteArray(1024)
            var read: Int = inputStream.read(buffer)
            while (read != -1) {
                out.write(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            inputStream.close()
            out.flush()
            out.close()
        } catch (e: Exception) {
            Log.e("tag", e.message)
        }

    }

    companion object {


        private val tempDirectoryName = "files"
        private val replacementDirectoryName = WireMockApp.FILES_ROOT

        private val dataDirectory = "/data/data"

        fun deleteRecursive(fileOrDirectory: File) {
            if (fileOrDirectory.isDirectory) {
                for (child in fileOrDirectory.listFiles()!!) {
                    deleteRecursive(child)
                }
            }

            fileOrDirectory.delete()
        }
    }

}