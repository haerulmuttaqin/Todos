package id.haeworks.haerulmuttaqin.route

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import id.haeworks.core.route.DetailRoute
import id.haeworks.core.route.ListRoute
import id.haeworks.todo_detail_presentation.DetailScreen
import id.haeworks.todo_detail_presentation.DetailViewModel
import id.haeworks.todo_list_presentation.ListScreen
import id.haeworks.todo_list_presentation.ListViewModel

fun NavGraphBuilder.detailRoute(
    navHostController: NavHostController,
) =
    composable<DetailRoute> { navBackStackEntry ->
        val viewModel: DetailViewModel = hiltViewModel()

        DetailScreen(
            navHostController = navHostController,
            state = viewModel.state.collectAsState().value,
            onEvent = viewModel::onEvent,
        )
    }