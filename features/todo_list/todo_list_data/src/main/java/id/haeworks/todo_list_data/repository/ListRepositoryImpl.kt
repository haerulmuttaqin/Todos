package id.haeworks.todo_list_data.repository

import id.haeworks.core.network.Resource
import id.haeworks.todo_list_data.datasource.remote.ListRemoteDataSource
import id.haeworks.todo_list_data.mapper.ListMapper.toDomainModel
import id.haeworks.todo_list_domain.model.Todo
import id.haeworks.todo_list_domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val remoteDataSource: ListRemoteDataSource,
) : ListRepository {

    override suspend fun getList(): Flow<Resource<List<Todo>>> = flow {
        try {
            val response = remoteDataSource.getList()
            if (response.isNotEmpty()) {
                emit(
                    Resource.Success(
                        data = response.map { it.toDomainModel() },
                        message = "Fetch successfully!"
                    )
                )
            } else emit(Resource.Empty())
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable.message))
        }
    }

}