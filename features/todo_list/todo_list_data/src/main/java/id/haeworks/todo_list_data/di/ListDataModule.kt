package id.haeworks.todo_list_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.haeworks.todo_list_data.datasource.remote.ListRemoteDataSource
import id.haeworks.todo_list_data.datasource.remote.ListRemoteService
import id.haeworks.todo_list_data.repository.ListRepositoryImpl
import id.haeworks.todo_list_domain.repository.ListRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import id.haeworks.core.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object ListDataModule {

    @Singleton
    @Provides
    fun provideMainRemoteService(
        client: OkHttpClient,
    ): ListRemoteService = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ListRemoteService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ListRemoteDataSource,
    ): ListRepository = ListRepositoryImpl(
        remoteDataSource,
    )

}