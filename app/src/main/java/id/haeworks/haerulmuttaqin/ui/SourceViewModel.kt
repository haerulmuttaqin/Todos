package id.haeworks.haerulmuttaqin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.haeworks.haerulmuttaqin.data.SourceRepository
import id.haeworks.haerulmuttaqin.db.SourceEntity
import id.haeworks.todo_list_presentation.ListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SourceUiState(
    val query: String = "",
    val allSource: List<String> = emptyList(),
    val containsSource: List<String> = emptyList()
)

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val repository: SourceRepository,
) : ViewModel() {

    var uiState = MutableStateFlow(SourceUiState())
        private set

    init {
        viewModelScope.launch {
            repository.getAll().collectLatest { result ->
                uiState.update {
                    it.copy(
                        allSource = result.map { it?.source ?: "" },
                    )
                }
            }
        }
    }

    fun getChar(query: String) {
        viewModelScope.launch {
            val result =
                uiState.value.allSource.filter { it.lowercase().contains(query.lowercase()) }

            if (result.isEmpty()) {
                repository.add(SourceEntity(id = 0, source = query.uppercase()))
            }

            val present = if (result.isEmpty()) listOf(query) else result.toString()
                .replace(",", "", ignoreCase = true)
                .replace("[", "").replace("]", "").split(" ").filter { it.isNotEmpty() }

            uiState.update {
                it.copy(
                    containsSource = present
                )
            }
        }
    }
}


