package id.haeworks.todo_detail_domain.use_case

import id.haeworks.core.network.Resource
import id.haeworks.todo_detail_domain.model.Todo
import id.haeworks.todo_detail_domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow


class GetDetail(private val repository: DetailRepository) {
    suspend operator fun invoke(id: Int): Flow<Resource<Todo>> =
        repository.getDetail(id = id)
}