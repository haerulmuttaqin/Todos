package id.haeworks.haerulmuttaqin.route

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import id.haeworks.core.route.ListRoute
import id.haeworks.todo_list_presentation.ListScreen
import id.haeworks.todo_list_presentation.ListViewModel

fun NavGraphBuilder.listRoute(
    navHostController: NavHostController,
) =
    composable<ListRoute> { navBackStackEntry ->
        val viewModel: ListViewModel = hiltViewModel()

        ListScreen(
            navHostController = navHostController,
            state = viewModel.state.collectAsState().value,
            onEvent = viewModel::onEvent,
        )
    }