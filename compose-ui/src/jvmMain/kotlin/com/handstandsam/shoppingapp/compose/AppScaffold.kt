package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount
import kotlinx.coroutines.flow.Flow


@Composable
fun AppScaffold(
    itemsInCart: Flow<List<ItemWithQuantity>>,
    showCartClicked: () -> Unit,
    logoutClicked: () -> Unit,
    homeUpClicked: (() -> Unit)? = null,
    title: String? = null,
    showCartStatus: Boolean = true,
    content: @Composable () -> Unit
) {
    val itemCount by itemsInCart.collectAsState(initial = listOf())

    val navigationIcon: @Composable (() -> Unit)? = if (homeUpClicked != null) {
        {
            IconButton(onClick = homeUpClicked) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    } else {
        null
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(title ?: "Shopping App")
                },
                navigationIcon = navigationIcon,
                actions = {
                    if (showCartStatus) {
                        ShoppingCartIconWithCount(itemCount = itemCount.totalItemCount()) {
                            showCartClicked()
                        }
                    }
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More...")
                    }
                    // 2022-08-28 08:52:01.207 7122-7122/com.handstandsam.shoppingapp E/AndroidRuntime: FATAL EXCEPTION: main
                    //     Process: com.handstandsam.shoppingapp, PID: 7122
                    //     java.lang.NoClassDefFoundError: Failed resolution of: Landroidx/compose/material/DesktopMenu_desktopKt;
                    //         at com.handstandsam.shoppingapp.compose.AppScaffoldKt$AppScaffold$1$2.invoke(AppScaffold.kt:75)
                    //         at com.handstandsam.shoppingapp.compose.AppScaffoldKt$AppScaffold$1$2.invoke(AppScaffold.kt:65)
                    //         at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:116)
                    //         at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:34)
                    //         at androidx.compose.material.AppBarKt$TopAppBar$1$3.invoke(AppBar.kt:576)
                    //         at androidx.compose.material.AppBarKt$TopAppBar$1$3.invoke(AppBar.kt:117)
                    // DropdownMenu(
                    //     expanded = showMenu,
                    //     onDismissRequest = { showMenu = false }
                    // ) {
                    //     DropdownMenuItem(
                    //         onClick = logoutClicked
                    //     ) {
                    //         Text(text = "Log out")
                    //     }
                    // }
                },
            )
        }
    ) {
        content()
    }
}


@Composable
fun ShoppingCartIconWithCount(itemCount: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.wrapContentSize()) {
        IconButton(onClick = {
            onClick()
        }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Cart"
            )
        }
        if (itemCount > 0) {
            val topCornerPadding = 6.dp
            val circleWidth = 15.dp
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopEnd)
                    .padding(
                        end = topCornerPadding,
                        top = topCornerPadding
                    )
            ) {
                Canvas(
                    modifier = Modifier
                        .size(circleWidth),
                    onDraw = {
                        drawCircle(color = Color.Red)
                    }
                )
                Text(
                    modifier = Modifier
                        .size(circleWidth),
                    text = "$itemCount",
                    fontSize = 10.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}