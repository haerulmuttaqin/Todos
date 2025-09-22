package id.haeworks.todo_list_domain.use_case

import id.haeworks.core.network.Resource
import id.haeworks.todo_list_domain.model.Todo
import id.haeworks.todo_list_domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow


class GetList(private val repository: ListRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Todo>>> =
        repository.getList()
}