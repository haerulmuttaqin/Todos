package id.haeworks.todo_list_presentation

import id.haeworks.core.model.SnackbarState
import id.haeworks.todo_list_domain.model.Todo

data class ListState(
    val snackbar: SnackbarState = SnackbarState(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val list: List<Todo> = emptyList(),
)
