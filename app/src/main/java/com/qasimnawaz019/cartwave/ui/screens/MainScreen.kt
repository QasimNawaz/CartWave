package com.qasimnawaz019.cartwave.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.BottomBar
import com.qasimnawaz019.cartwave.ui.components.BottomBarScreenInfo
import com.qasimnawaz019.cartwave.ui.components.CartWaveScaffold
import com.qasimnawaz019.cartwave.ui.screens.graphs.HomeNavigationGraph

@Composable
fun MainScreen(
    onNavigate: (route: String) -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    CartWaveScaffold(bottomBar = { BottomBar(navController = navController) }, topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = R.drawable.ic_arrow_left,
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(color = MaterialTheme.colorScheme.onBackground, text = "Hi, Qasim")
                Text(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    text = "Let's go shopping"
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }) { innerPadding ->
        Column {
            HomeNavigationGraph(
                navController = navController,
                startDestination = BottomBarScreenInfo.Home.route,
                innerPadding = innerPadding,
                onNavigate = onNavigate
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen({})
}