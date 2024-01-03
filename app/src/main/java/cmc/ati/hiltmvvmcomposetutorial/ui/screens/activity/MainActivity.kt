package cmc.ati.hiltmvvmcomposetutorial.ui.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cmc.ati.hiltmvvmcomposetutorial.ui.screens.mainscreen.MainScreen
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.HiltMVVMComposeTutorialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.isLoading.value
            }
        }
        setContent {
            HiltMVVMComposeTutorialTheme {
                MainScreen()
            }
        }
    }
}