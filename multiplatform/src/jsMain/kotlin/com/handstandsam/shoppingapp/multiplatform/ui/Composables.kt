package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text


@Composable
fun WrappedPreformattedText(str: String) {
    Pre(
        {
            style {
                whiteSpace("pre-wrap")
            }
        }
    ) { Text(str) }
}


@Composable
fun ImageAndTextRow(label: String, imageUrl: String, onClick: () -> Unit = {}) {
    Div({
        onClick { onClick() }
    }) {
        P {
            Text(label)
        }
        Img(
            attrs = {
                style {
                    height(200.px)
                }
            },
            src = imageUrl
        )
    }
}