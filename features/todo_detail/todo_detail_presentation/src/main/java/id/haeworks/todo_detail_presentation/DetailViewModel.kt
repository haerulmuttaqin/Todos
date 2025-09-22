package id.haeworks.todo_detail_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import id.haeworks.core.model.SnackbarState
import id.haeworks.core.network.Resource
import id.haeworks.core.route.DetailRoute
import id.haeworks.core.util.NetworkHelper
import id.haeworks.core.util.extension.orZero
import id.haeworks.todo_detail_domain.use_case.DetailUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import id.haeworks.core.R

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCases: DetailUseCases,
    private val networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var resultJob: Job? = null
    private var job: Job? = null

    private val route =
        savedStateHandle.toRoute<DetailRoute>()
    var state = MutableStateFlow(DetailState())
        private set

    init {
        onEvent(DetailEvent.OnGetDetail(route.id.orZero(), isRefresh = false))
    }

    fun onEvent(event: DetailEvent) {
        when (event) {

            is DetailEvent.SetSnackbar -> state.update {
                it.copy(
                    snackbar = state.value.snackbar.copy(
                        message = event.snackbarState.message,
                        messageId = event.snackbarState.messageId,
                        isSuccess = event.snackbarState.isSuccess
                    )
                )
            }

            is DetailEvent.SetLoading -> state.update { it.copy(isLoading = event.isLoading) }

            is DetailEvent.ResetSnackbar -> resetSnackbar(event.isDelay)

            is DetailEvent.OnGetDetail -> getSubjects(event.id, event.isRefresh)

            is DetailEvent.SetRefreshing -> state.update {
                it.copy(isRefreshing = event.isRefreshing)
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

    private fun getSubjects(id: Int, isRefreshing: Boolean = false) {
        resultJob?.cancel()
        resultJob = viewModelScope.launch(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                if (isRefreshing) {
                    onEvent(DetailEvent.SetRefreshing(true))
                } else {
                    onEvent(DetailEvent.SetLoading(true))
                }
                useCases.getDetail(id).collect {
                    when (it) {
                        is Resource.Success -> {
                            state.update { data ->
                                data.copy(
                                    detail = it.data,
                                )
                            }
                        }

                        else -> {
                            onEvent(
                                DetailEvent.SetSnackbar(
                                    SnackbarState(message = it.message, isSuccess = false)
                                )
                            )

                        }
                    }
                    if (isRefreshing) {
                        onEvent(DetailEvent.SetRefreshing(false))
                    } else {
                        onEvent(DetailEvent.SetLoading(false))
                    }
                }
            } else {
                onEvent(
                    DetailEvent.SetSnackbar(
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