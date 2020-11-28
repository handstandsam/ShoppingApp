package com.handstandsam.shoppingapp.compose

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.models.Category
import dev.chrisbanes.accompanist.coil.CoilImage

class CategoryListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val categories = mutableStateOf(listOf<Category>())

    init {
        setContent {
            LazyColumnFor(items = categories.value) { category ->
                CategoryView(category) {
                    CategoryActivity.launch(context, category)
                }
            }
        }
    }
}

@Composable
fun CategoryView(category: Category, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize(
                align = Alignment.CenterStart
            )
    ) {
        CoilImage(
            data = category.image,
            contentScale = ContentScale.Crop,
            fadeIn = true,
            loading = {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(120.dp)
                .clickable(onClick = onClick)
        )
        Text(
            text = category.label,
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            style = TextStyle(
                fontSize = 32.sp
            )
        )
    }

}