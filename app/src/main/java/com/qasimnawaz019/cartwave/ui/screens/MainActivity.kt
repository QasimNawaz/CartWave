package com.qasimnawaz019.cartwave.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.qasimnawaz019.cartwave.ui.screens.graphs.RootNavigationGraph
import com.qasimnawaz019.cartwave.ui.theme.MaterialTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MaterialTheme {
                val uiState: UiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.startDestination != null) {
                    RootNavigationGraph(navController = rememberNavController(),
                        startDestination = uiState.startDestination!!,
                        errorMessage = uiState.error,
                        onPositiveClick = {
                            finish()
                        })
                }
            }
        }
    }

}