package cmc.ati.hiltmvvmcomposetutorial.ui.screens.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.Genre
import cmc.ati.hiltmvvmcomposetutorial.navigation.Navigation
import cmc.ati.hiltmvvmcomposetutorial.ui.theme.HiltMVVMComposeTutorialTheme

@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreName = remember { mutableStateOf("") }
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    Scaffold() {
        Navigation(navController, Modifier.padding(it), genreList.value)
        Box(modifier = Modifier.fillMaxWidth()) {
            Column() {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
