package id.haeworks.todo_list_data.datasource.remote

import id.haeworks.todo_list_data.model.TodoDTO
import javax.inject.Inject

class ListRemoteDataSource @Inject constructor(
    private val remoteService: ListRemoteService
) {
    suspend fun getList(): List<TodoDTO> = remoteService.getList()
}