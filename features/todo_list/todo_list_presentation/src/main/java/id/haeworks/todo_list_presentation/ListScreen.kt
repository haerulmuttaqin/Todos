package id.haeworks.todo_list_presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import id.haeworks.core.R
import id.haeworks.core.route.DetailRoute
import id.haeworks.core.ui.showDefaultSnackbar
import id.haeworks.todo_list_domain.model.Todo
import id.haeworks.todo_list_presentation.component.TodoListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navHostController: NavHostController,
    state: ListState,
    onEvent: (ListEvent) -> Unit,
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    LaunchedEffect(state.snackbar) {
        if (!state.snackbar.message.isNullOrBlank() || state.snackbar.messageId != null) {
            onEvent(ListEvent.ResetSnackbar())
            snackbarState.showDefaultSnackbar(
                context = context,
                snackbar = state.snackbar,
                actionLabel = context.getString(R.string.ok),
            ).apply {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        onEvent(ListEvent.ResetSnackbar(false))
                    }
                }
            }

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = state.isRefreshing,
            onRefresh = {
                onEvent(ListEvent.OnGetList(isRefresh = true))
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = scrollState,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(state.list) { todo ->
                    TodoListItem(
                        item = todo,
                        onCheckedChange = {
                            onEvent(ListEvent.OnCheckedChange(todo))
                        }
                    ) {
                        navHostController.navigate(DetailRoute(todo.id))
                    }
                    HorizontalDivider()
                }
            }
        }
    }

}