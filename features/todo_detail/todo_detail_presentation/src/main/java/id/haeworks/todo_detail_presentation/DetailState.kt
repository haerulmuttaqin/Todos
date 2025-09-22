package id.haeworks.todo_detail_presentation

import id.haeworks.core.model.SnackbarState
import id.haeworks.todo_detail_domain.model.Todo

data class DetailState(
    val snackbar: SnackbarState = SnackbarState(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val detail: Todo? = null,
)
