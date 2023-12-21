package com.qasimnawaz019.cartwave.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qasimnawaz019.cartwave.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onNavigate: (route: String) -> Unit
) {

    val pages = listOf(
        CategoryPage.All, CategoryPage.Clothing, CategoryPage.Footwear, CategoryPage.Bags
    )
    val pagerState = rememberPagerState { 4 }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ImageSlider()
        CategoriesRow(
            pages, pagerState
        )
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = false,
            beyondBoundsPageCount = 0
        ) { position ->
            HomePagerProducts(onNavigate, pages[position].title)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesRow(pages: List<CategoryPage>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        pages.forEachIndexed { index, categoryPage ->
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = if (pagerState.settledPage == index) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .size(50.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clickable { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    painter = painterResource(id = categoryPage.image),
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.primaryContainer,
                )
            }
        }
    }
}


sealed class CategoryPage(
    @DrawableRes val image: Int, val title: String
) {

    object All : CategoryPage(
        image = R.drawable.ic_list,
        title = "All",
    )

    object Clothing : CategoryPage(
        image = R.drawable.ic_clothing,
        title = "Clothing and Accessories",
    )

    object Footwear : CategoryPage(
        image = R.drawable.ic_footwear,
        title = "Footwear",
    )

    object Bags : CategoryPage(
        image = R.drawable.ic_bag_wallet_belt,
        title = "Bags, Wallets & Belts",
    )
}
