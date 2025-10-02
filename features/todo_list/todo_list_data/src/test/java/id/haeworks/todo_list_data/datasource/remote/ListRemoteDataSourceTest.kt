package id.haeworks.todo_list_data.datasource.remote

import id.haeworks.todo_list_data.model.TodoDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListRemoteDataSourceTest {

    private lateinit var remoteService: ListRemoteService
    private lateinit var remoteDataSource: ListRemoteDataSource

    @Before
    fun setup() {
        remoteService = mockk()
        remoteDataSource = ListRemoteDataSource(remoteService)
    }

    @Test
    fun `getList should return list of todos when service returns data`() = runTest {
        // Given
        val expectedTodos = listOf(
            TodoDTO(
                id = 1,
                title = "Test Todo 1",
                userId = 1,
                completed = false
            ),
            TodoDTO(
                id = 2,
                title = "Test Todo 2",
                userId = 1,
                completed = true
            )
        )
        coEvery { remoteService.getList() } returns expectedTodos

        // When
        val result = remoteDataSource.getList()

        // Then
        assertEquals(expectedTodos, result)
    }

    @Test
    fun `getList should return empty list when service returns empty list`() = runTest {
        // Given
        val expectedTodos = emptyList<TodoDTO>()
        coEvery { remoteService.getList() } returns expectedTodos

        // When
        val result = remoteDataSource.getList()

        // Then
        assertEquals(expectedTodos, result)
    }

    @Test
    fun `getList should propagate exception when service throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { remoteService.getList() } throws exception

        // When & Then
        try {
            remoteDataSource.getList()
            assert(false) { "Expected exception to be thrown" }
        } catch (e: RuntimeException) {
            assertEquals("Network error", e.message)
        }
    }
}
