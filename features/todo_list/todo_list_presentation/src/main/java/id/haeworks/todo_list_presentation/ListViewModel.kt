package id.haeworks.todo_list_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.haeworks.core.model.SnackbarState
import id.haeworks.core.network.Resource
import id.haeworks.core.util.NetworkHelper
import id.haeworks.todo_list_domain.use_case.ListUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import id.haeworks.core.R

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: ListUseCases,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private var resultJob: Job? = null
    private var job: Job? = null

    var state = MutableStateFlow(ListState())
        private set

    init {
        onEvent(ListEvent.OnGetList(isRefresh = false))
    }

    fun onEvent(event: ListEvent) {
        when (event) {

            is ListEvent.SetSnackbar -> state.update {
                it.copy(
                    snackbar = state.value.snackbar.copy(
                        message = event.snackbarState.message,
                        messageId = event.snackbarState.messageId,
                        isSuccess = event.snackbarState.isSuccess
                    )
                )
            }

            is ListEvent.SetLoading -> state.update { it.copy(isLoading = event.isLoading) }

            is ListEvent.ResetSnackbar -> resetSnackbar(event.isDelay)

            is ListEvent.OnGetList -> getSubjects(event.isRefresh)

            is ListEvent.SetRefreshing -> state.update {
                it.copy(isRefreshing = event.isRefreshing)
            }

            is ListEvent.OnCheckedChange -> state.update {
                it.copy(list = state.value.list.map { item ->
                    if (item.id == event.item.id) {
                        item.copy(completed = !item.completed)
                    } else {
                        item
                    }
                })
            }

        }
    }

    private fun resetSnackbar(isDelay: Boolean = true) {
        job?.cancel()
        job = viewModelScope.launch {
            if (isDelay) {
                delay(3000L)
            }
            state.value = state.value.copy(
                snackbar = state.value.snackbar.copy(message = null, messageId = null)
            )
        }
        job?.start()
    }

    private fun getSubjects(isRefreshing: Boolean = false) {
        resultJob?.cancel()
        resultJob = viewModelScope.launch(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                if (isRefreshing) {
                    onEvent(ListEvent.SetRefreshing(true))
                } else {
                    onEvent(ListEvent.SetLoading(true))
                }
                useCases.getList().collect {
                    when (it) {
                        is Resource.Success -> {
                            state.update { data ->
                                data.copy(
                                    list = it.data.orEmpty(),
                                )
                            }
                        }

                        else -> {
                            onEvent(
                                ListEvent.SetSnackbar(
                                    SnackbarState(message = it.message, isSuccess = false)
                                )
                            )

                        }
                    }
                    if (isRefreshing) {
                        onEvent(ListEvent.SetRefreshing(false))
                    } else {
                        onEvent(ListEvent.SetLoading(false))
                    }
                }
            } else {
                onEvent(
                    ListEvent.SetSnackbar(
                        SnackbarState(
                            messageId = R.string.please_check_internet_connection,
                            isSuccess = false
                        )
                    )
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        resultJob?.cancel()
        resultJob = null
        job?.cancel()
        job = null
    }

}