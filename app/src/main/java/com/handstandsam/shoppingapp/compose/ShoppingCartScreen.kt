package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.handstandsam.shoppingapp.features.checkout.ShoppingCartViewModel
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.flow.Flow


@Composable
fun ShoppingCartScreen(
    itemsInCart: Flow<List<ItemWithQuantity>>,
    checkoutClicked: () -> Unit,
    logoutClicked: () -> Unit,
    homeUpClicked: () -> Unit,
    shoppingCartViewModel: ShoppingCartViewModel
) {
    AppScaffold(
        itemsInCart = itemsInCart,
        showCartClicked = checkoutClicked,
        logoutClicked = logoutClicked,
        title = "Cart",
        homeUpClicked = homeUpClicked,
        showCartStatus = false
    ) {
        val state by shoppingCartViewModel.states.collectAsState(
            initial = ShoppingCartViewModel.State()
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = state.cartStatusLabel,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                state.items.forEach { itemWithQuantity ->
                    item {
                        ShoppingCartItemRow(itemWithQuantity = itemWithQuantity,
                            incrementClicked = {
                                shoppingCartViewModel.send(
                                    ShoppingCartViewModel.Intention.IncrementClicked(itemWithQuantity)
                                )
                            },
                            decrementClicked = {
                                shoppingCartViewModel.send(
                                    ShoppingCartViewModel.Intention.DecrementClicked(itemWithQuantity)
                                )
                            }
                        )
                    }
                }
            }

            Button(
                onClick = checkoutClicked,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Checkout")
            }
        }
    }
}

@Composable
fun ShoppingCartItemRow(
    itemWithQuantity: ItemWithQuantity,
    incrementClicked: () -> Unit,
    decrementClicked: () -> Unit
) {
    val height = 100.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        CoilImage(
            modifier = Modifier
                .size(height)
                .aspectRatio(1.0f),
            imageModel = itemWithQuantity.item.image,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularRevealedEnabled = true
        )
        Spacer(modifier = Modifier.width(16.dp))
        val label = itemWithQuantity.quantity.toString() + " x " + itemWithQuantity.item.label
        Text(
            modifier = Modifier
                .height(height)
                .align(CenterVertically),
            text = label
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            ColoredCircleWithText(
                color = Color.Green,
                text = "+",
                onClick = incrementClicked
            )
            ColoredCircleWithText(
                color = Color.Red,
                text = "-",
                onClick = incrementClicked
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun ColoredCircleWithText(
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    val circleWidth = 32.dp
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
    ) {
        Canvas(
            modifier = Modifier
                .clickable { onClick() }
                .size(circleWidth),
            onDraw = {
                drawCircle(color = color)
            }
        )
        Text(
            modifier = Modifier
                .size(circleWidth),
            text = text,
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}