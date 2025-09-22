package id.haeworks.todo_detail_data.datasource.remote

import id.haeworks.todo_detail_data.model.TodoDTO
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val remoteService: DetailRemoteService
) {
    suspend fun getDetail(id: Int): TodoDTO? = remoteService.getDetail(id)
}