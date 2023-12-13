package com.qasimnawaz019.cartwave.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.qasimnawaz019.cartwave.ui.screens.graphs.RootNavigationGraph
import com.qasimnawaz019.cartwave.ui.theme.MaterialTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, true)
        installSplashScreen()
        setContent {
            MaterialTheme {
                val uiState: UiState by viewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.startDestination != null) {
                    Log.d("startDestination", "${uiState.startDestination}")
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