package cmc.ati.hiltmvvmcomposetutorial.di

import cmc.ati.hiltmvvmcomposetutorial.data.datasource.remote.ApiService
import cmc.ati.hiltmvvmcomposetutorial.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provides RemoteDataRepository for access api service method
     */
    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
    ): MovieRepository {
        return MovieRepository(
            apiService
        )
    }

}