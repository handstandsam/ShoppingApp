import com.handstandsam.shoppingapp.multiplatform.ui.NavRoute
import com.handstandsam.shoppingapp.multiplatform.ui.navController
import io.ktor.http.encodeURLParameter
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Backstack {
    private val scope = CoroutineScope(Dispatchers.Unconfined)

    /**
     * Simple dequeing array for a backstack.
     */
    private val historyEntries = ArrayDeque<NavRoute>()

    private fun addToBackstack(navRoute: NavRoute) {
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
            is NavRoute.CartScreen -> {
                "cart"
            }
        }
        println("addToBackstack($key, $navRoute)")
        if (navRoute != historyEntries.lastOrNull()) {
            historyEntries.addLast(navRoute)
            window.history.pushState(key, "", key)
        }
    }

    fun init() {
        scope.launch {
            navController.collect {
                println("collectedNavRoute: $it")
                addToBackstack(it)
            }
        }

        window.onpopstate = {
            val historyEntry: NavRoute? = historyEntries.removeLastOrNull()
            historyEntry?.let {
                val prevHistoryItem: NavRoute? = historyEntries.removeLastOrNull()
                prevHistoryItem?.let {
                    println("Found History Entry for $prevHistoryItem")
                    navController.tryEmit(prevHistoryItem)
                }
            }
        }
    }
}