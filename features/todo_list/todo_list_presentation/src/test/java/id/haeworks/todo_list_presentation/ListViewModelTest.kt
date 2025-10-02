package id.haeworks.todo_list_presentation

import id.haeworks.core.model.SnackbarState
import id.haeworks.core.network.Resource
import id.haeworks.core.util.NetworkHelper
import id.haeworks.todo_list_domain.model.Todo
import id.haeworks.todo_list_domain.use_case.GetList
import id.haeworks.todo_list_domain.use_case.ListUseCases
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var useCases: ListUseCases
    private lateinit var getList: GetList
    private lateinit var networkHelper: NetworkHelper
    private lateinit var viewModel: ListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        getList = mockk()
        useCases = mockk {
            every { this@mockk.getList } returns getList
        }
        networkHelper = mockk()
        
        viewModel = ListViewModel(useCases, networkHelper)
    }

    @Test
    fun `initial state should have default values`() {
        // Then
        val state = viewModel.state.value
        assertEquals(SnackbarState(), state.snackbar)
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertTrue(state.list.isEmpty())
    }

    @Test
    fun `onEvent SetLoading should update loading state`() {
        // When
        viewModel.onEvent(ListEvent.SetLoading(true))

        // Then
        assertTrue(viewModel.state.value.isLoading)

        // When
        viewModel.onEvent(ListEvent.SetLoading(false))

        // Then
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent SetRefreshing should update refreshing state`() {
        // When
        viewModel.onEvent(ListEvent.SetRefreshing(true))

        // Then
        assertTrue(viewModel.state.value.isRefreshing)

        // When
        viewModel.onEvent(ListEvent.SetRefreshing(false))

        // Then
        assertFalse(viewModel.state.value.isRefreshing)
    }

    @Test
    fun `onEvent SetSnackbar should update snackbar state`() {
        // Given
        val snackbarState = SnackbarState(
            message = "Test message",
            isSuccess = true
        )

        // When
        viewModel.onEvent(ListEvent.SetSnackbar(snackbarState))

        // Then
        val state = viewModel.state.value
        assertEquals("Test message", state.snackbar.message)
        assertTrue(state.snackbar.isSuccess)
    }

    @Test
    fun `onEvent OnCheckedChange should toggle todo completion status`() {
        // Given
        val todo1 = Todo(
            id = 1,
            title = "Todo 1",
            userId = 1,
            completed = false
        )
        val todo2 = Todo(
            id = 2,
            title = "Todo 2",
            userId = 1,
            completed = true
        )
        val initialList = listOf(todo1, todo2)
        
        // Set initial state
        viewModel.state.value = viewModel.state.value.copy(list = initialList)

        // When
        viewModel.onEvent(ListEvent.OnCheckedChange(todo1))

        // Then
        val updatedList = viewModel.state.value.list
        assertTrue(updatedList[0].completed) // todo1 should be completed
        assertTrue(updatedList[1].completed) // todo2 should remain completed

        // When
        viewModel.onEvent(ListEvent.OnCheckedChange(todo2))

        // Then
        val finalList = viewModel.state.value.list
        assertTrue(finalList[0].completed) // todo1 should remain completed
        assertFalse(finalList[1].completed) // todo2 should be uncompleted
    }

    @Test
    fun `onEvent OnGetList should fetch data successfully when network is available`() = runTest {
        // Given
        val todos = listOf(
            Todo(
                id = 1,
                title = "Test Todo 1",
                userId = 1,
                completed = false
            ),
            Todo(
                id = 2,
                title = "Test Todo 2",
                userId = 1,
                completed = true
            )
        )
        val successResource = Resource.Success(
            data = todos,
            message = "Success"
        )
        
        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getList() } returns flowOf(successResource)

        // When
        viewModel.onEvent(ListEvent.OnGetList(isRefresh = false))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(todos, state.list)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onEvent OnGetList should show error when network is not available`() = runTest {
        // Given
        every { networkHelper.isNetworkConnected() } returns false

        // When
        viewModel.onEvent(ListEvent.OnGetList(isRefresh = false))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state.snackbar.messageId != null)
        assertFalse(state.snackbar.isSuccess)
    }

    @Test
    fun `onEvent OnGetList should handle error response`() = runTest {
        // Given
        val errorResource = Resource.Error<List<Todo>>("Network error")
        
        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getList() } returns flowOf(errorResource)

        // When
        viewModel.onEvent(ListEvent.OnGetList(isRefresh = false))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals("Network error", state.snackbar.message)
        assertFalse(state.snackbar.isSuccess)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onEvent OnGetList should handle empty response`() = runTest {
        // Given
        val emptyResource = Resource.Empty<List<Todo>>()
        
        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getList() } returns flowOf(emptyResource)

        // When
        viewModel.onEvent(ListEvent.OnGetList(isRefresh = false))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state.snackbar.message != null)
        assertFalse(state.snackbar.isSuccess)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onEvent OnGetList with refresh should set refreshing state`() = runTest {
        // Given
        val todos = listOf(
            Todo(
                id = 1,
                title = "Test Todo",
                userId = 1,
                completed = false
            )
        )
        val successResource = Resource.Success(
            data = todos,
            message = "Success"
        )
        
        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getList() } returns flowOf(successResource)

        // When
        viewModel.onEvent(ListEvent.OnGetList(isRefresh = true))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(todos, state.list)
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `onEvent ResetSnackbar should clear snackbar message after delay`() = runTest {
        // Given
        viewModel.state.value = viewModel.state.value.copy(
            snackbar = SnackbarState(message = "Test message")
        )

        // When
        viewModel.onEvent(ListEvent.ResetSnackbar(isDelay = false))
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(null, state.snackbar.message)
    }
}
