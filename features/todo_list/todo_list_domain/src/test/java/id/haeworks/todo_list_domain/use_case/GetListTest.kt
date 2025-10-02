package id.haeworks.todo_list_domain.use_case

import id.haeworks.core.network.Resource
import id.haeworks.todo_list_domain.model.Todo
import id.haeworks.todo_list_domain.repository.ListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetListTest {

    private lateinit var repository: ListRepository
    private lateinit var getList: GetList

    @Before
    fun setup() {
        repository = mockk()
        getList = GetList(repository)
    }

    @Test
    fun `invoke should return success resource when repository returns success`() = runTest {
        // Given
        val expectedTodos = listOf(
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
            data = expectedTodos,
            message = "Success"
        )
        coEvery { repository.getList() } returns flowOf(successResource)

        // When
        val result = getList().first()

        // Then
        assertTrue(result is Resource.Success)
        val successResult = result as Resource.Success
        assertEquals(expectedTodos, successResult.data)
        assertEquals("Success", successResult.message)
    }

    @Test
    fun `invoke should return error resource when repository returns error`() = runTest {
        // Given
        val errorResource = Resource.Error<List<Todo>>("Network error")
        coEvery { repository.getList() } returns flowOf(errorResource)

        // When
        val result = getList().first()

        // Then
        assertTrue(result is Resource.Error)
        val errorResult = result as Resource.Error
        assertEquals("Network error", errorResult.message)
    }

    @Test
    fun `invoke should return empty resource when repository returns empty`() = runTest {
        // Given
        val emptyResource = Resource.Empty<List<Todo>>()
        coEvery { repository.getList() } returns flowOf(emptyResource)

        // When
        val result = getList().first()

        // Then
        assertTrue(result is Resource.Empty)
    }
}
