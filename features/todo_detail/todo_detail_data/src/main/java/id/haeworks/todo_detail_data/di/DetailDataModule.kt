package id.haeworks.todo_detail_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.haeworks.core.BuildConfig
import id.haeworks.todo_detail_data.datasource.remote.DetailRemoteDataSource
import id.haeworks.todo_detail_data.datasource.remote.DetailRemoteService
import id.haeworks.todo_detail_data.repository.DetailRepositoryImpl
import id.haeworks.todo_detail_domain.repository.DetailRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailDataModule {

    @Singleton
    @Provides
    fun provideMainRemoteService(
        client: OkHttpClient,
    ): DetailRemoteService = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(DetailRemoteService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: DetailRemoteDataSource,
    ): DetailRepository = DetailRepositoryImpl(
        remoteDataSource,
    )

}