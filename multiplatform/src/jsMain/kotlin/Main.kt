import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.cart.InMemoryShoppingCartDao
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.ItemWithQuantity
import com.handstandsam.shoppingapp.models.totalItemCount
import com.handstandsam.shoppingapp.multiplatform.ui.CategoryDetailViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.HomeViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.ItemDetailViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.NavRoute
import com.handstandsam.shoppingapp.multiplatform.ui.WrappedPreformattedText
import com.handstandsam.shoppingapp.multiplatform.ui.navController
import io.ktor.http.encodeURLParameter
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable


fun main() {

    val scope = CoroutineScope(Dispatchers.Unconfined)

    val historyEntries: MutableMap<String, NavRoute> = mutableMapOf()

    fun addToBackstack(navRoute: NavRoute) {
        val key = "#/" + when (navRoute) {
            is NavRoute.HomeScreen -> {
                "home"
            }
            is NavRoute.CategoryDetailScreen -> {
                "categories/${navRoute.category.label.encodeURLParameter()}"
            }
            is NavRoute.ItemDetailScreen -> {
                "items/${navRoute.item.label.encodeURLParameter()}"
            }
        }
        println("addToBackstack($key, $navRoute)")
        historyEntries[key] = navRoute
        window.history.pushState(key, "", "$key")
    }

    val shoppingCart = ShoppingCart(InMemoryShoppingCartDao())
    val homeViewModel = HomeViewModel()
    val categoryDetailViewModel = CategoryDetailViewModel()
    val itemDetailViewModel = ItemDetailViewModel(shoppingCart = shoppingCart)

    @Composable
    fun ShoppingApp() {
        val itemsInCartFlow: Flow<List<ItemWithQuantity>> = shoppingCart.itemsInCart
        val itemsInCartCount: List<ItemWithQuantity> by itemsInCartFlow.collectAsState(listOf())
        val collectedNavRoute: NavRoute by navController.collectAsState()

        println("collectedNavRoute: $collectedNavRoute")

        addToBackstack(collectedNavRoute)
        Div {
            Text("${itemsInCartCount.totalItemCount()} Item(s) in Cart")
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
        }
    }

    /** Entry Point */
    renderComposable(rootElementId = "root") {
        ShoppingApp()
    }

    window.onpopstate = {
        val key = it.state.toString()
        println(it)
        println("key: $key")
        val historyEntry: NavRoute? = historyEntries[key]
        if (historyEntry != null) {
            println("Found History Entry for $historyEntry")
            navController.tryEmit(historyEntry)
        } else {
            println("No History Entry for $key")
        }
    }
}