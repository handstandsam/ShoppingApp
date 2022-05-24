package com.handstandsam.shoppingapp.multiplatform.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.H6
import org.jetbrains.compose.web.dom.Img
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
        classes("list-group-item", "list-group-item-action")
    }) {
        Div(attrs = { classes("row") }) {
            Div(attrs = {
                classes("col")
            }) {
                Img(
                    attrs = {
                        classes("flex-shrink-0")
                    },
                    src = imageUrl
                )
            }
            Div(attrs = {
                classes("col")
            }) {
                H3 {
                    Text(label)
                }
            }
        }
    }
}

@Composable
fun PrimaryButton(label: String, onClick: () -> Unit = {}) {
    ShoppingAppButton(label, ButtonType.PRIMARY, onClick)
}

enum class ButtonType(val cssClass: String) {
    PRIMARY("btn-primary"), GREEN("btn-success"), RED("btn-danger")
}

@Composable
fun ShoppingAppButton(label: String, buttonType: ButtonType, onClick: () -> Unit = {}) {
    Button(
        attrs = {
            classes("btn", buttonType.cssClass)
            style {
                when(buttonType){
                    ButtonType.PRIMARY ->{}
                    ButtonType.GREEN ->
                        backgroundColor(Color.green)
                    ButtonType.RED ->
                        backgroundColor(Color.red)
                }
            }
            onClick {
                onClick()
            }
        }
    ) { Text(label) }
}

@Composable
fun ShoppingAppList(content: (@Composable () -> Unit)) {
    Div(attrs =
    {
        classes("list-group")
    }) { content() }
}