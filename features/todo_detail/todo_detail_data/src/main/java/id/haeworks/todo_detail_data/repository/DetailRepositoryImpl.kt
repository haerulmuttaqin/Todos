package id.haeworks.todo_detail_data.repository

import id.haeworks.core.network.Resource
import id.haeworks.todo_detail_data.datasource.remote.DetailRemoteDataSource
import id.haeworks.todo_detail_data.mapper.DetailMapper.toDomainModel
import id.haeworks.todo_detail_domain.model.Todo
import id.haeworks.todo_detail_domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailRemoteDataSource,
) : DetailRepository {

    override suspend fun getDetail(id: Int): Flow<Resource<Todo>> = flow {
        try {
            val response = remoteDataSource.getDetail(id)
            if (response != null) {
                emit(
                    Resource.Success(
                        data = response.toDomainModel(),
                        message = "Fetch successfully!"
                    )
                )
            } else emit(Resource.Empty())
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable.message))
        }
    }

}