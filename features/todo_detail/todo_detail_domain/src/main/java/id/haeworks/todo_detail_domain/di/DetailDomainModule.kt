package id.haeworks.todo_detail_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.haeworks.todo_detail_domain.repository.DetailRepository
import id.haeworks.todo_detail_domain.use_case.GetDetail
import id.haeworks.todo_detail_domain.use_case.DetailUseCases

@Module
@InstallIn(SingletonComponent::class)
object DetailDomainModule {
    @Provides
    fun provideUseCases(
        repository: DetailRepository
    ): DetailUseCases = DetailUseCases(
        getDetail = GetDetail(repository),
    )
}