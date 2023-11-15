package com.qasimnawaz019.cartwave.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun VerticalGridProductsShimmer() {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f), //darker grey (90% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (30% opacity)
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition() // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
    repeat(3) {
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(2) {
                CartWaveSurface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                        .height(230.dp)
                        .clip(shape = MaterialTheme.shapes.medium),
                    elevation = 2.dp,
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(7f)
                                .background(brush)
                                .clip(shape = MaterialTheme.shapes.medium),
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                                .background(brush),
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(7f)
                                    .background(brush),
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(3f)
                                    .background(brush),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScope.ProductDetailShimmer() {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f), //darker grey (90% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (30% opacity)
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition() // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )

    Spacer(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(brush),
    )
    CartWaveSurface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        elevation = 20.dp,
        shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
    ) {

    }


}