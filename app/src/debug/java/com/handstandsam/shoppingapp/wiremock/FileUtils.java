package com.handstandsam.shoppingapp.wiremock;

import android.content.Context;
import android.util.Log;

import com.github.tomakehurst.wiremock.core.WireMockApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

public class FileUtils {


    private static Context contextForAssets;

    public FileUtils(Context contextForAssets) {
        this.contextForAssets = contextForAssets;
    }

    private static String tempDirectoryName = "files";
    private static String replacementDirectoryName = WireMockApp.FILES_ROOT;

    private static String dataDirectory = "/data/data";

    private static String getPackageName() {
        return contextForAssets.getPackageName();
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    public void copyFileOrDir(String path) {
        String assets[];
        try {
            assets = contextForAssets.getAssets().list(path);
            Timber.v(path + " - " + assets);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath = dataDirectory + "/" + getPackageName() + "/" + path;
                fullPath = fullPath.replace(tempDirectoryName, replacementDirectoryName);
                File directory = new File(fullPath);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        InputStream in;
        OutputStream out;
        try {
            //context is not defined
            in = contextForAssets.getAssets().open(filename);
            String newFileName = dataDirectory + "/" + getPackageName() + "/" + filename;
            newFileName = newFileName.replace(tempDirectoryName, replacementDirectoryName);
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


}