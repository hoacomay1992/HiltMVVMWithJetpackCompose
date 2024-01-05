package cmc.ati.hiltmvvmcomposetutorial.ui.screens.bottomnavigation.nowplaying

import androidx.lifecycle.ViewModel
import cmc.ati.hiltmvvmcomposetutorial.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(val repo: MovieRepository) : ViewModel() {
}