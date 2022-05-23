import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.cart.InMemoryShoppingCartDao
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount
import com.handstandsam.shoppingapp.multiplatform.ui.ButtonType
import com.handstandsam.shoppingapp.multiplatform.ui.CartViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.CategoryDetailViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.HomeViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.ItemDetailViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.NavRoute
import com.handstandsam.shoppingapp.multiplatform.ui.ShoppingAppButton
import com.handstandsam.shoppingapp.multiplatform.ui.WrappedPreformattedText
import com.handstandsam.shoppingapp.multiplatform.ui.navController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable


fun main() {

    val scope = CoroutineScope(Dispatchers.Unconfined)


    val shoppingCart = ShoppingCart(InMemoryShoppingCartDao())
    val homeViewModel = HomeViewModel()
    val categoryDetailViewModel = CategoryDetailViewModel()
    val cartViewModel = CartViewModel(shoppingCart = shoppingCart)
    val itemDetailViewModel = ItemDetailViewModel(shoppingCart = shoppingCart)

    @Composable
    fun ShoppingApp() {
        val itemsInCartFlow: Flow<List<ItemWithQuantity>> = shoppingCart.itemsInCart
        val itemsInCartCount: List<ItemWithQuantity> by itemsInCartFlow.collectAsState(listOf())
        val collectedNavRoute: NavRoute by navController.collectAsState()

        Div {
            Text("${itemsInCartCount.totalItemCount()} Item(s) in Cart")
            ShoppingAppButton(label = "View Cart", buttonType = ButtonType.PRIMARY) {
                scope.launch {
                    navController.tryEmit(NavRoute.CartScreen(shoppingCart.latestItemsInCart()))
                }
            }
            WrappedPreformattedText(itemsInCartCount.toString())
        }
        when (val navRoute = collectedNavRoute) {
            NavRoute.HomeScreen -> {
                homeViewModel.HomeScreen()
            }
            is NavRoute.CategoryDetailScreen -> {
                categoryDetailViewModel.CategoryDetailScreen(navRoute.category)
            }
            is NavRoute.ItemDetailScreen -> {
                itemDetailViewModel.ItemDetailScreen(navRoute.item)
            }
            is NavRoute.CartScreen -> {
                cartViewModel.CartScreen(navRoute.itemsWithQuantity)
            }
        }
    }

    /** Entry Point */
    renderComposable(rootElementId = "root") {
        ShoppingApp()
    }

    Backstack.init()
}