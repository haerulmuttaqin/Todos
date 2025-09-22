package id.haeworks.todo_detail_domain.repository

import id.haeworks.core.network.Resource
import id.haeworks.todo_detail_domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetail(id: Int): Flow<Resource<Todo>>
}