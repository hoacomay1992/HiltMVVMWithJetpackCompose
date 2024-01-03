package cmc.ati.hiltmvvmcomposetutorial.data.repository

import androidx.paging.PagingData
import cmc.ati.hiltmvvmcomposetutorial.data.model.BaseModel
import cmc.ati.hiltmvvmcomposetutorial.data.model.Genres
import cmc.ati.hiltmvvmcomposetutorial.data.model.MovieItem
import cmc.ati.hiltmvvmcomposetutorial.data.model.artist.Artist
import cmc.ati.hiltmvvmcomposetutorial.data.model.artist.ArtistDetail
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.MovieDetail
import cmc.ati.hiltmvvmcomposetutorial.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>>
    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>>
    suspend fun search(searchKey: String): Flow<DataState<BaseModel>>
    suspend fun genreList(): Flow<DataState<Genres>>
    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>>
    suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>>
    fun nowPlayingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun popularPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun topRatedPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun upcomingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>>
}