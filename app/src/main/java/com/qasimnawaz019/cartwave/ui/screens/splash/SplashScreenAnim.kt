package com.qasimnawaz019.cartwave.ui.screens.splash

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import com.qasimnawaz019.cartwave.R

@Composable
fun SplashScreenAnim() {

    val dampingRatio = remember {
        mutableStateOf(0.5f)
    }
    val stiffness = remember {
        mutableStateOf(800f)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
//                .weight(1f, true)
                .background(Color.Cyan)
                .zIndex(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            val shape = RoundedCornerShape(6.dp)
            val size = min(maxHeight, maxWidth) / 3
            var onTop by remember {
                mutableStateOf(true)
            }
            val offset =
                animateIntOffsetAsState(
                    targetValue = if (onTop) IntOffset(0, with(LocalDensity.current) {
                        (maxHeight / 8).roundToPx()
                    }) else IntOffset(0,
                        with(LocalDensity.current) {
                            (maxHeight / 2 - size / 2).roundToPx()
                        }), spring(dampingRatio.value, stiffness.value)
                )
            Box(
                modifier = Modifier
                    .offset { offset.value }
                    .shadow(
                        8.dp,
                        shape,
                    )
                    .background(Color.Blue)
                    .size(size)
                    .clickable { onTop = onTop.not() }
            )

        }
    }


//    val size = 192.dp
//    val maxHeight = maxHeight
//    val offset =
//        animateIntOffsetAsState(
//            targetValue = if (onTop) IntOffset(0, with(LocalDensity.current) {
//                (maxHeight / 8).roundToPx()
//            }) else IntOffset(0, with(LocalDensity.current) {
//                (maxHeight / 2 - size / 2).roundToPx()
//            }), spring(dampingRatio.value, stiffness.value),
//            finishedListener = {
//                drawTitle.not()
//            }, label = ""
//        )
//
//    Box(
//        modifier = Modifier
//            .offset { offset.value }
//            .size(size),
////                .clickable { onTop = onTop.not() },
//        contentAlignment = Alignment.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_logo),
//            contentDescription = "Logo",
//        )
//    }
}