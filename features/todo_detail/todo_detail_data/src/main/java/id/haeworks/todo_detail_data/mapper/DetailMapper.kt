package id.haeworks.todo_detail_data.mapper

import id.haeworks.core.util.extension.orFalse
import id.haeworks.core.util.extension.orZero
import id.haeworks.todo_detail_data.model.TodoDTO
import id.haeworks.todo_detail_domain.model.Todo

object DetailMapper {
    fun TodoDTO?.toDomainModel() = Todo(
        id = this?.id.orZero(),
        title = this?.title.orEmpty(),
        userId = this?.userId.orZero(),
        completed = this?.completed.orFalse()
    )
}