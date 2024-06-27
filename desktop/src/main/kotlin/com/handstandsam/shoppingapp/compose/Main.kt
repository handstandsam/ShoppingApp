@file:OptIn(ExperimentalMaterialApi::class)

package com.handstandsam.shoppingapp.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.handstandsam.shoppingapp.cart.InMemoryShoppingCartDao
import com.handstandsam.shoppingapp.cart.SessionManager
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.features.category.CategoryViewModel
import com.handstandsam.shoppingapp.features.checkout.ShoppingCartViewModel
import com.handstandsam.shoppingapp.features.home.HomeViewModel
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

object DesktopAppGraph {
    val shoppingCart = ShoppingCart(InMemoryShoppingCartDao())
    val scope = CoroutineScope(Dispatchers.IO)

    val sessionManager = object : SessionManager {
        override val currentUser: MutableStateFlow<User?> = MutableStateFlow(
            User(
                firstname = "Desktop", lastname = "User"
            )
        )
        override val isLoggedIn: Boolean = currentUser.value != null

        override fun updateCurrentUser(user: User?) {
            currentUser.value = user
        }

        override suspend fun logout() {
            updateCurrentUser(null)
        }
    }

    val mockAccount = ProduceMockAccount()
    val networkGraph = object : NetworkGraph {
        override val categoryRepo: CategoryRepo =
            object : CategoryRepo {
                override suspend fun getCategories(): Response<List<Category>> {
                    val categories = mockAccount.getCategories()
                    return Response.Success(categories)
                }
            }

        override val itemRepo: ItemRepo =
            object : ItemRepo {
                override suspend fun getItemsForCategory(categoryLabel: String): Response<List<Item>> {
                    val itemsForCategory = mockAccount.getItemsForCategory(categoryLabel)
                    if (itemsForCategory != null) {
                        return Response.Success(itemsForCategory)
                    } else {
                        return Response.Failure()
                    }
                }
            }

        override val userRepo: UserRepo =
            object : UserRepo {
                override suspend fun login(loginRequest: LoginRequest): Response<User> {
                    return Response.Success(mockAccount.getUser())
                }
            }
    }
}

enum class ScreenType {
    Home, Category, Cart
}

val scope = DesktopAppGraph.scope
val shoppingCart = DesktopAppGraph.shoppingCart
val sessionManager = DesktopAppGraph.sessionManager
val categoryRepo = DesktopAppGraph.networkGraph.categoryRepo
val itemRepo = DesktopAppGraph.networkGraph.itemRepo

val basicNav = MutableStateFlow(ScreenType.Home)

fun main1() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "ShoppingApp Desktop",
        state = rememberWindowState(
            position = WindowPosition(alignment = Alignment.Center),
        ),
    ) {
        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CurrentScreen()
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
        val count = remember { mutableStateOf(0) }
        MaterialTheme {
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value++
                    }) {
                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
                }
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value = 0
                    }) {
                    Text("Reset")
                }
            }
        }
    }
}


@Composable
fun ShoppingCartPage() {
    val shoppingCartViewModel = ShoppingCartViewModel(scope, shoppingCart)
    ShoppingCartScreen(
        itemsInCart = shoppingCart.itemsInCart,
        homeUpClicked = {
            basicNav.value = ScreenType.Home
        },
        checkoutClicked = {},
        logoutClicked = {},
        shoppingCartViewModel = shoppingCartViewModel,
        shoppingAppImageLoader = DesktopShoppingAppImageLoader()
    )
}

val categoryViewModel = CategoryViewModel(
    scope = scope,
    itemRepo = itemRepo,
)

@Composable
fun CategoryPage() {
    CategoryScreen(
        itemsInCart = shoppingCart.itemsInCart,
        logoutClicked = {},
        shoppingAppImageLoader = DesktopShoppingAppImageLoader(),
        categoryViewModel = categoryViewModel,
        showCartClicked = {
            basicNav.value = ScreenType.Cart
        },
        homeUpClicked = {
            basicNav.value = ScreenType.Home
        },
        itemClicked = { item ->
            scope.launch {
                shoppingCart.incrementItemInCart(item)
                basicNav.value = ScreenType.Cart
            }
        }
    )
}

val homeViewModel = HomeViewModel(
    scope,
    sessionManager,
    categoryRepo
).apply {
    scope.launch {
        sideEffects
            .onEach { homeViewModelSideEffect ->
                when (homeViewModelSideEffect) {
                    is HomeViewModel.SideEffect.LaunchCategoryActivity -> {
                        categoryViewModel.send(
                            CategoryViewModel.Intention.CategoryLabelSet(
                                homeViewModelSideEffect.category.label
                            )
                        )
                        basicNav.value = ScreenType.Category
                    }

                    HomeViewModel.SideEffect.Logout -> TODO()
                }
            }
            .collect()
    }
}

@Composable
fun HomePage() {
    HomeScreen(
        itemsInCart = shoppingCart.itemsInCart,
        logoutClicked = {},
        shoppingAppImageLoader = DesktopShoppingAppImageLoader(),
        homeViewModel = homeViewModel,
        showCartClicked = {
            basicNav.value = ScreenType.Cart
        }
    )
}

@Composable
fun CurrentScreen() {
    val curr = basicNav.collectAsState()
    when (curr.value) {
        ScreenType.Home -> HomePage()
        ScreenType.Category -> CategoryPage()
        ScreenType.Cart -> ShoppingCartPage()
    }
}