package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

class DesktopShoppingAppImageLoader : ShoppingAppImageLoader {

    private fun loadNetworkImage(link: String): ImageBitmap {
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val bufferedImage = ImageIO.read(inputStream)

        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", stream)
        val byteArray = stream.toByteArray()

        return org.jetbrains.skia.Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    @Composable
    override fun loadImage(modifier: Modifier, image: String) {
        val imageBitmap = loadNetworkImage(image)
        Image(
            painter = BitmapPainter(imageBitmap),
            modifier = modifier,
            contentDescription = null
        )
    }
}