package id.haeworks.todo_list_domain.repository

import id.haeworks.core.network.Resource
import id.haeworks.todo_list_domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    suspend fun getList(): Flow<Resource<List<Todo>>>
}