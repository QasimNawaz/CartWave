package com.qasimnawaz019.cartwave.ui.screens.splash

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.flatten
import androidx.navigation.NavHostController
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.utils.navigateTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.math.floor


@Composable
fun SplashScreen(
    navController: NavHostController,
    startDestination: String,
    viewModel: SplashScreenViewModel = getViewModel()
) {
//    val window = (LocalView.current.context as Activity).window
//    val rectangle = Rect()
//    window.decorView.getWindowVisibleDisplayFrame(rectangle)
//    val statusBarHeight = rectangle.top
//    Log.d("SplashScreen", "statusBarHeight: $statusBarHeight")

    val shouldContinue = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = shouldContinue, block = {
        navigateTo(
            navController = navController, destination = startDestination, true
        )
    })

    val coroutineScope = rememberCoroutineScope()

    // New Animation
    val dampingRatio = remember {
        mutableStateOf(0.5f)
    }
    val stiffness = remember {
        mutableStateOf(800f)
    }
    var onTop by remember {
        mutableStateOf(false)
    }

    val path = remember {
        CartWavePath.path.toPath()
    }
    val bounds = path.getBounds()

    val totalLength = remember {
        val pathMeasure = PathMeasure()
        pathMeasure.setPath(path, false)
        pathMeasure.length
    }
    val lines = remember {
        path.asAndroidPath().flatten(0.5f)
    }
    val progress = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        coroutineScope.launch {
            delay(1000)
            onTop = onTop.not()
        }
    })
    BoxWithConstraints(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val logoSize = 192.dp
        val nameSize = 300.dp
        Log.d("SplashScreen", "maxHeight: $maxHeight")
//        val maxHeight = (maxHeight.value - rectangle.top / 3).dp
        val maxHeight = maxHeight
        val logoOffset = animateIntOffsetAsState(targetValue = if (onTop) IntOffset(
            0,
            with(LocalDensity.current) {
                (maxHeight / 8).roundToPx()
            }) else IntOffset(0, with(LocalDensity.current) {
            (maxHeight / 2 - logoSize / 2).roundToPx()
        }), spring(dampingRatio.value, stiffness.value), finishedListener = {
            Log.d("SplashScreen", "finishedListener")
            coroutineScope.launch {
                progress.animateTo(1f, animationSpec = tween(3000), block = {
                    if (this.value == 1f) {
//                        viewModel.isUserLoggedIn()
                        shouldContinue.value = true
                    }
                })
            }
        }, label = ""
        )

        Box(modifier = Modifier
            .offset { logoOffset.value }
            .size(logoSize),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
            )
        }

        val startColor = MaterialTheme.colorScheme.primary
        val endColor = MaterialTheme.colorScheme.primary
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Canvas(modifier = Modifier.size(nameSize), onDraw = {
                val currentLength = totalLength * progress.value
                lines.forEach { line ->
                    if (line.startFraction * totalLength < currentLength) {
//                            val startColor = interpolateColors(line.startFraction, colors)
//                            val endColor = interpolateColors(line.endFraction, colors)
//                            val startOffset = Offset(line.start.x, line.start.y)
//                            val endOffset = Offset(line.end.x, line.end.y)
                        drawLine(
                            brush = Brush.linearGradient(listOf(startColor, endColor)),
                            start = Offset(line.start.x, line.start.y),
                            end = Offset(line.end.x, line.end.y),
                            strokeWidth = 7f,
                            cap = StrokeCap.Square,
                        )
                    }
                }
            })
        }
    }

}

private val colors = listOf(
    Color(0xFF3FCEBC),
    Color(0xFF3CBCEB),
    Color(0xFF5F96E7),
    Color(0xFF816FE3),
    Color(0xFF9F5EE2),
    Color(0xFFBD4CE0),
    Color(0xFFDE589F),
    Color(0xFFFF645E),
    Color(0xFFFDA859),
    Color(0xFFFAEC54),
    Color(0xFF9EE671),
    Color(0xFF67E282),
    Color(0xFF3FCEBC)
)

private object CartWavePath {
    val path = PathParser().parsePathString(
        "M49,163.4C35.8,163.4 25.13,158.47 17,148.6C8.87,138.6 4.8,125.4 4.8,109C4.8,92.6 7.6,77.73 13.2,64.4C18.93,50.93 27.07,40 37.6,31.6C48.13,23.2 59.8,19 72.6,19C77.4,19 81.67,19.73 85.4,21.2C95,14.8 104.93,11.6 115.2,11.6C120,11.6 124.47,12.33 128.6,13.8C132.87,15.13 136,16.8 138,18.8C136.93,23.33 135.2,27.47 132.8,31.2C126.53,26.53 120.2,24.2 113.8,24.2C107.4,24.2 101.53,25.47 96.2,28C102.47,34.67 105.6,44.13 105.6,56.4C105.6,68.53 102.4,79.6 96,89.6C89.6,99.6 81.6,104.6 72,104.6C66.27,104.6 61.6,102.2 58,97.4C54.53,92.6 52.8,86.6 52.8,79.4C52.8,62.07 59.27,46.4 72.2,32.4C61.67,32.8 53.13,39.73 46.6,53.2C40.07,66.53 36.8,82.67 36.8,101.6C36.8,129.2 44.8,143 60.8,143C71.73,143 83.4,136.2 95.8,122.6L102.8,128.4C101.07,131.47 98.2,135.27 94.2,139.8C90.33,144.33 86.47,148.13 82.6,151.2C78.73,154.27 73.73,157.07 67.6,159.6C61.47,162.13 55.27,163.4 49,163.4ZM91.8,58.4C91.8,47.47 89.33,40 84.4,36C78.53,41.2 73.8,47.53 70.2,55C66.73,62.47 65,70.2 65,78.2C65,82.33 65.73,85.73 67.2,88.4C68.8,91.07 70.8,92.4 73.2,92.4C78.4,92.4 82.8,88.73 86.4,81.4C90,74.07 91.8,66.4 91.8,58.4ZM169.58,78.2C169.85,77.93 170.58,76.2 171.78,73C178.05,73 186.45,74.87 196.98,78.6C193.65,89.67 190.98,101.27 188.98,113.4C186.98,125.4 185.98,134 185.98,139.2C185.98,144.27 186.52,146.8 187.58,146.8C188.38,146.8 191.32,145.53 196.38,143L198.78,141.8L202.38,148.8C201.18,149.87 199.58,151.2 197.58,152.8C195.72,154.4 192.12,156.6 186.78,159.4C181.45,162.07 176.65,163.4 172.38,163.4C164.12,163.4 159.32,159.67 157.98,152.2C149.45,159.67 141.72,163.4 134.78,163.4C127.98,163.4 122.18,160.73 117.38,155.4C112.72,150.07 110.38,141.67 110.38,130.2C110.38,112.33 114.05,98.27 121.38,88C128.72,77.6 137.58,72.4 147.98,72.4C155.45,72.4 162.65,74.33 169.58,78.2ZM146.78,146.4C149.98,146.4 153.58,145.13 157.58,142.6C158.52,123.8 161.65,105.2 166.98,86.8C162.98,85.07 159.58,84.2 156.78,84.2C151.98,84.2 147.85,88.93 144.38,98.4C141.05,107.73 139.38,118.07 139.38,129.4C139.38,140.73 141.85,146.4 146.78,146.4ZM210.08,161.6H208.68C208.41,160.53 208.28,158.13 208.28,154.4C208.28,150.53 210.08,140.27 213.68,123.6C217.28,106.93 219.08,97.4 219.08,95C219.08,90.87 217.21,86.33 213.48,81.4L211.68,79L211.88,76.4C219.08,74.4 230.61,73.4 246.48,73.4C248.08,76.87 248.88,81.73 248.88,88C250.35,85.6 253.41,82.4 258.08,78.4C262.74,74.4 267.55,72.4 272.48,72.4C279.68,72.4 283.28,78.67 283.28,91.2C282.61,92 281.68,93.07 280.48,94.4C279.41,95.6 277.14,97.33 273.68,99.6C270.35,101.87 267.08,103.27 263.88,103.8C263.74,103.8 262.61,102 260.48,98.4C258.35,94.8 256.74,93 255.68,93C252.35,94.2 249.41,96.2 246.88,99C240.74,126.6 237.68,144.47 237.68,152.6C237.68,155.53 237.74,157.6 237.88,158.8C233.21,160.67 223.95,161.6 210.08,161.6ZM345.44,74.8C345.17,78.13 344.04,82 342.04,86.4C336.97,85.87 332.17,85.6 327.64,85.6H324.04C317.9,115.33 314.84,133.87 314.84,141.2C314.84,145.07 315.64,147 317.24,147C318.97,147 323.44,145.33 330.64,142L334.24,148.6C322.37,158.47 311.37,163.4 301.24,163.4C296.57,163.4 292.7,161.93 289.64,159C286.7,156.07 285.24,152.2 285.24,147.4C285.24,142.47 285.77,136.93 286.84,130.8C288.04,124.67 289.64,117.2 291.64,108.4C293.64,99.47 295.1,92.27 296.04,86.8C291.1,87.2 287.44,87.6 285.04,88C284.77,86.4 284.64,84.27 284.64,81.6C284.64,78.8 284.84,76.53 285.24,74.8H297.84C298.9,67.6 299.44,60.8 299.44,54.4L299.24,48.2V47.6C309.64,44 319.44,42.2 328.64,42.2C329.17,44.87 329.44,48.2 329.44,52.2C329.44,56.2 328.3,63.73 326.04,74.8H345.44ZM377.82,136.4L379.02,102.4C366.22,100.8 355.49,96.47 346.82,89.4C338.15,82.33 333.82,72.87 333.82,61C333.82,51.27 338.62,41.87 348.22,32.8C357.82,23.6 369.29,19 382.62,19C402.22,19 412.02,31.73 412.02,57.2C412.02,64.67 411.49,76.13 410.42,91.6C409.49,106.93 409.02,118.53 409.02,126.4C409.02,134.27 411.15,138.2 415.42,138.2C417.95,138.2 421.62,136.07 426.42,131.8C431.35,127.4 435.49,122.67 438.82,117.6C441.75,87.47 443.22,69.47 443.22,63.6C443.22,57.73 443.15,53.73 443.02,51.6C443.02,49.47 442.89,47.4 442.62,45.4C442.49,43.4 442.35,41.93 442.22,41C442.22,39.93 441.95,38.53 441.42,36.8C441.02,35.07 440.75,34.07 440.62,33.8C440.62,33.53 440.29,32.4 439.62,30.4C439.09,28.4 438.82,27.33 438.82,27.2C445.62,23.6 456.75,20.87 472.22,19V19.2C473.69,23.87 474.42,30.6 474.42,39.4C474.42,48.07 473.09,63.2 470.42,84.8C467.75,106.4 466.42,120.73 466.42,127.8C466.42,134.73 468.42,138.2 472.42,138.2C476.55,138.2 481.49,134.6 487.22,127.4C493.09,120.2 498.42,110.8 503.22,99.2C508.15,87.6 511.22,76.33 512.42,65.4C512.42,54.6 506.75,42.13 495.42,28C498.09,22.27 501.55,17.13 505.82,12.6C510.09,7.93 513.69,4.73 516.62,3L521.02,0.2C525.95,0.2 530.15,3.73 533.62,10.8C537.22,17.73 539.02,25.93 539.02,35.4C539.02,44.73 536.49,56.8 531.42,71.6C526.35,86.27 519.95,100.4 512.22,114C504.62,127.6 495.62,139.27 485.22,149C474.95,158.6 465.15,163.4 455.82,163.4C451.02,163.4 446.82,161 443.22,156.2C439.62,151.4 437.75,145.27 437.62,137.8C431.89,145.8 425.82,152.07 419.42,156.6C413.02,161.13 406.62,163.4 400.22,163.4C393.82,163.4 388.49,160.8 384.22,155.6C379.95,150.4 377.82,144 377.82,136.4ZM380.82,57.6C380.82,49.33 380.02,43.73 378.42,40.8C376.95,37.73 374.02,36.2 369.62,36.2C365.35,36.2 360.35,38.6 354.62,43.4C349.02,48.2 346.22,54.47 346.22,62.2C346.22,69.93 349.55,76.33 356.22,81.4C363.02,86.47 370.89,89 379.82,89C380.49,76.33 380.82,65.87 380.82,57.6ZM595.56,78.2C595.83,77.93 596.56,76.2 597.76,73C604.03,73 612.43,74.87 622.96,78.6C619.63,89.67 616.96,101.27 614.96,113.4C612.96,125.4 611.96,134 611.96,139.2C611.96,144.27 612.49,146.8 613.56,146.8C614.36,146.8 617.29,145.53 622.36,143L624.76,141.8L628.36,148.8C627.16,149.87 625.56,151.2 623.56,152.8C621.69,154.4 618.09,156.6 612.76,159.4C607.43,162.07 602.63,163.4 598.36,163.4C590.09,163.4 585.29,159.67 583.96,152.2C575.43,159.67 567.69,163.4 560.76,163.4C553.96,163.4 548.16,160.73 543.36,155.4C538.69,150.07 536.36,141.67 536.36,130.2C536.36,112.33 540.03,98.27 547.36,88C554.69,77.6 563.56,72.4 573.96,72.4C581.43,72.4 588.63,74.33 595.56,78.2ZM572.76,146.4C575.96,146.4 579.56,145.13 583.56,142.6C584.49,123.8 587.63,105.2 592.96,86.8C588.96,85.07 585.56,84.2 582.76,84.2C577.96,84.2 573.83,88.93 570.36,98.4C567.03,107.73 565.36,118.07 565.36,129.4C565.36,140.73 567.83,146.4 572.76,146.4ZM651.46,72.4C657.32,72.4 661.66,73.6 664.46,76C667.39,78.4 668.85,83.33 668.85,90.8C668.85,94.8 667.66,103 665.26,115.4C662.85,127.67 661.66,135.13 661.66,137.8C661.66,143.13 662.72,145.8 664.85,145.8C669.92,145.8 675.46,142.07 681.46,134.6C687.59,127 690.79,120.67 691.05,115.6L677.46,100.4C677.99,94.67 679.39,89.47 681.66,84.8C683.92,80.13 686.05,76.87 688.05,75L690.85,72.4C696.59,72.4 700.92,73.93 703.85,77C706.79,80.07 708.26,83.67 708.26,87.8C708.26,98.33 705.66,109.47 700.46,121.2C695.26,132.93 688.32,142.93 679.66,151.2C670.99,159.33 662.19,163.4 653.26,163.4C647.66,163.4 643.32,161.67 640.26,158.2C637.32,154.73 635.85,150.2 635.85,144.6C635.85,138.87 636.26,131.2 637.05,121.6C637.99,112 638.46,102.47 638.46,93C638.46,90.87 637.66,88.33 636.05,85.4C634.59,82.47 633.05,80.13 631.46,78.4L632.05,76C639.26,73.6 645.72,72.4 651.46,72.4ZM709.97,126.4C709.97,109.73 714.97,96.6 724.97,87C735.1,77.27 746.57,72.4 759.37,72.4C767.23,72.4 773.7,74.33 778.77,78.2C783.83,82.07 786.37,87.27 786.37,93.8C786.37,100.2 784.7,105.6 781.37,110C778.17,114.4 774.23,117.8 769.57,120.2C760.1,124.87 751.43,127.8 743.57,129L738.77,129.6C739.7,142.13 744.83,148.4 754.17,148.4C757.37,148.4 760.77,147.6 764.37,146C767.97,144.4 770.77,142.8 772.77,141.2L775.77,138.8L780.57,145.2C779.5,146.67 777.37,148.6 774.17,151C770.97,153.4 767.97,155.4 765.17,157C757.43,161.27 748.97,163.4 739.77,163.4C730.57,163.4 723.3,160.13 717.97,153.6C712.63,147.07 709.97,138 709.97,126.4ZM738.57,119.8C745.37,118.6 750.77,115.73 754.77,111.2C758.77,106.67 760.77,100.8 760.77,93.6C760.77,86.4 758.63,82.8 754.37,82.8C749.3,82.8 745.37,87.13 742.57,95.8C739.9,104.33 738.57,112.33 738.57,119.8Z"
    )
}

private fun interpolateColors(
    progress: Float,
    colorsInput: List<Color>,
): Color {
    if (progress == 1f) return colorsInput.last()

    val scaledProgress = progress * (colorsInput.size - 1)
    val oldColor = colorsInput[scaledProgress.toInt()]
    val newColor = colorsInput[(scaledProgress + 1f).toInt()]
    val newScaledAnimationValue = scaledProgress - floor(scaledProgress)
    return lerp(start = oldColor, stop = newColor, fraction = newScaledAnimationValue)
}