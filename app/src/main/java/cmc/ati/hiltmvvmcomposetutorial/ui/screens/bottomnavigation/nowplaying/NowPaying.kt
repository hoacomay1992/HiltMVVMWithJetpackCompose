package cmc.ati.hiltmvvmcomposetutorial.ui.screens.bottomnavigation.nowplaying

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cmc.ati.hiltmvvmcomposetutorial.data.model.GenreId
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.Genre
import cmc.ati.hiltmvvmcomposetutorial.ui.component.MovieItemList

@Composable
fun NowPlaying(navController: NavController, genres: ArrayList<Genre>? = null) {
    val nowPlayViewModel = hiltViewModel<NowPlayingViewModel>()
    MovieItemList(
        navController = navController,
        movies = nowPlayViewModel.nowPlayingMovies,
        genres = genres,
        selectedName = nowPlayViewModel.selectedGenre.value
    ) {
        nowPlayViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            nowPlayViewModel.selectedGenre.value = it
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NowPlayingPreview() {
//    NowPlaying()
//}
