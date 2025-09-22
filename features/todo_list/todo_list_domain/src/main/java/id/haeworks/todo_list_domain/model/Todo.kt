package id.haeworks.todo_list_domain.model


data class Todo(
    val id: Int,
    val title: String,
    val userId: Int,
    val completed: Boolean,
)