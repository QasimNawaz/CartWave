package com.qasimnawaz019.cartwave.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.screens.onboarding.PageIndicator

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun ImageSlider() {
    val pages = listOf(
        ImageSliderPage.First, ImageSliderPage.Second, ImageSliderPage.Third
    )
    val pagerState = rememberPagerState { 3 }
    Box(
        Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Column {
            HorizontalPager(
                modifier = Modifier.weight(9f),
                state = pagerState,
            ) { position ->
                Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(120.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                                .padding(end = 100.dp, start = 10.dp, top = 10.dp, bottom = 10.dp)
                        ) {
                            Text(
                                text = pages[position].title,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = pages[position].description,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Light,
                                lineHeight = 16.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    AsyncImage(
                        modifier = Modifier
                            .height(160.dp)
                            .width(150.dp)
                            .padding(end = 10.dp, bottom = 10.dp),
                        model = pages[position].image,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }
            PageIndicator(
                numberOfPages = pages.size,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                pagerState = pagerState,
                defaultRadius = 5.dp,
                space = 8.dp,
                selectedLength = 30.dp,
                animationDurationInMillis = 100
            )
        }
    }
}

sealed class ImageSliderPage(
    @DrawableRes val image: Int, val title: String, val description: String
) {

    object First : ImageSliderPage(
        image = R.drawable.image_men_inner,
        title = "Seasonal Showstoppers",
        description = "Get ready to slay the season! Dive into our curated seasonal picks and make a statement with your wardrobe"
    )

    object Second : ImageSliderPage(
        image = R.drawable.ic_womens_fashion,
        title = "Luxury Within Reach",
        description = "Indulge in luxury without breaking the bank. Discover premium fashion at prices that delight"
    )

    object Third : ImageSliderPage(
        image = R.drawable.ic_mens_fashion,
        title = "Fashion for Every Occasion",
        description = "From casual hangouts to elegant soirées, find the perfect outfit for every event. Elevate your style effortlessly."
    )
}