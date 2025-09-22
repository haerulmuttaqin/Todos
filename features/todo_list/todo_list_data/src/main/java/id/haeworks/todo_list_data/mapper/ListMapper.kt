package id.haeworks.todo_list_data.mapper

import id.haeworks.core.util.extension.orFalse
import id.haeworks.core.util.extension.orZero
import id.haeworks.todo_list_data.model.TodoDTO
import id.haeworks.todo_list_domain.model.Todo

object ListMapper {
    fun TodoDTO?.toDomainModel() = Todo(
        id = this?.id.orZero(),
        title = this?.title.orEmpty(),
        userId = this?.userId.orZero(),
        completed = this?.completed.orFalse()
    )
}