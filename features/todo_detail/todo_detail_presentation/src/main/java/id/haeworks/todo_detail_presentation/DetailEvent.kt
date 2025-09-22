package id.haeworks.todo_detail_presentation

import id.haeworks.core.model.SnackbarState
import id.haeworks.todo_detail_domain.model.Todo

sealed class DetailEvent {
    data class SetLoading(val isLoading: Boolean) : DetailEvent()
    data class SetRefreshing(val isRefreshing: Boolean) : DetailEvent()
    data class SetSnackbar(val snackbarState: SnackbarState) : DetailEvent()
    data class ResetSnackbar(val isDelay: Boolean = true) : DetailEvent()
    data class OnGetDetail(val id: Int, val isRefresh: Boolean = true) : DetailEvent()
}
