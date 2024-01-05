package cmc.ati.hiltmvvmcomposetutorial.ui.screens.bottomnavigation.nowplaying

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.Genre

@Composable
fun NowPlaying(navController: NavController, genres: ArrayList<Genre>? = null) {
    val nowPlayingViewModel = hiltViewModel<NowPlayingViewModel>()
    Mov
}

//@Preview(showBackground = true)
//@Composable
//fun NowPlayingPreview() {
//    NowPlaying()
//}
