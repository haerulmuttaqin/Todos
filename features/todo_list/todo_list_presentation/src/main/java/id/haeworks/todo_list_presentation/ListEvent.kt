package id.haeworks.todo_list_presentation

import id.haeworks.core.model.SnackbarState
import id.haeworks.todo_list_domain.model.Todo

sealed class ListEvent {
    data class SetLoading(val isLoading: Boolean) : ListEvent()
    data class SetRefreshing(val isRefreshing: Boolean) : ListEvent()
    data class SetSnackbar(val snackbarState: SnackbarState) : ListEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : ListEvent()
    data class OnGetList(val isRefresh: Boolean = true) : ListEvent()
    data class OnCheckedChange(val item: Todo) : ListEvent()
}
