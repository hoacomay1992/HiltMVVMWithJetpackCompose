package cmc.ati.hiltmvvmcomposetutorial.data.datasource.remote

import cmc.ati.hiltmvvmcomposetutorial.data.model.BaseModel
import cmc.ati.hiltmvvmcomposetutorial.data.model.Genres
import cmc.ati.hiltmvvmcomposetutorial.data.model.artist.Artist
import cmc.ati.hiltmvvmcomposetutorial.data.model.artist.ArtistDetail
import cmc.ati.hiltmvvmcomposetutorial.data.model.moviedetail.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("movie/popular")
    suspend fun popularMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("movie/top_rated")
    suspend fun topRatedMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("movie/upcoming")
    suspend fun upcomingMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): MovieDetail

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovie(
        @Path("movieId") movieId: Int,
        @Query("page") one: Int,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("search/movie?page=1&include_adult=false")
    suspend fun search(
        @Query("query") searchKey: String, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("genre/movie/list")
    suspend fun genreList(@Query("api_key") api_key: String = ApiURL.API_KEY): Genres

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("movie/{movieId}/credits")
    suspend fun movieCredit(
        @Path("movieId") movieId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): Artist

    @GET("person/{personId}")
    suspend fun artistDetail(
        @Path("personId") personId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): ArtistDetail
}