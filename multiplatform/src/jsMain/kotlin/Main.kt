import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.handstandsam.shoppingapp.multiplatform.ui.CategoryDetailViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.HomeViewModel
import com.handstandsam.shoppingapp.multiplatform.ui.NavRoutes
import com.handstandsam.shoppingapp.multiplatform.ui.navController
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable


fun main() {

    @Composable
    fun ShoppingApp() {
        val navRoute: NavRoutes by navController.collectAsState()

        val homeViewModel = HomeViewModel()
        val categoryDetailViewModel = CategoryDetailViewModel()


        println("Screen: ${navRoute}")
        when (navRoute) {
            NavRoutes.HomeScreen -> {
                homeViewModel.HomeScreen()
            }
            is NavRoutes.CategoryDetailScreen -> {
                val category = (navRoute as NavRoutes.CategoryDetailScreen).category
                categoryDetailViewModel.CategoryDetailScreen(category)
            }
            is NavRoutes.ItemDetailScreen -> {
                Text("brooklyn owen2")
            }
        }

    }

    /** Entry Point */
    renderComposable(rootElementId = "root") {
        Text("brooklyn owen1")
        ShoppingApp()
    }
}