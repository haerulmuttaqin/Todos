package id.haeworks.todo_list_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.haeworks.todo_list_domain.repository.ListRepository
import id.haeworks.todo_list_domain.use_case.GetList
import id.haeworks.todo_list_domain.use_case.ListUseCases

@Module
@InstallIn(SingletonComponent::class)
object ListDomainModule {
    @Provides
    fun provideUseCases(
        repository: ListRepository
    ): ListUseCases = ListUseCases(
        getList = GetList(repository),
    )
}