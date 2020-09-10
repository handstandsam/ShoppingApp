package com.handstandsam.shoppingapp.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.setContent
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler

class ComposeActivity : AppCompatActivity() {

//    override fun onBackPressed() {
//        if (!backPressHandler.handle()) {
//            super.onBackPressed()
//        }
//    }

//    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text("hi")
//            MyApplicationTheme {
//                Providers(
//                    AmbientBackPressHandler provides backPressHandler
//                ) {
//                    // Your root composable goes here
//                    Root.Content(Root.Routing.UsMap)
//                }
//            }
        }
    }
}


