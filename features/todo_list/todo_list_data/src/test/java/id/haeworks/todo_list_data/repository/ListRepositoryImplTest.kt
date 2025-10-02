package id.haeworks.todo_list_data.repository

import id.haeworks.core.network.Resource
import id.haeworks.todo_list_data.datasource.remote.ListRemoteDataSource
import id.haeworks.todo_list_data.model.TodoDTO
import id.haeworks.todo_list_domain.model.Todo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ListRepositoryImplTest {

    private lateinit var remoteDataSource: ListRemoteDataSource
    private lateinit var repository: ListRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        repository = ListRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `getList should return success resource when remote data source returns data`() = runTest {
        // Given
        val todoDTOs = listOf(
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
        coEvery { remoteDataSource.getList() } returns todoDTOs

        // When
        val result = repository.getList().first()

        // Then
        assertTrue(result is Resource.Success)
        val successResult = result as Resource.Success
        assertEquals(2, successResult.data?.size)
        assertEquals("Fetch successfully!", successResult.message)
        
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
        assertEquals(expectedTodos, successResult.data)
    }

    @Test
    fun `getList should return empty resource when remote data source returns empty list`() = runTest {
        // Given
        coEvery { remoteDataSource.getList() } returns emptyList()

        // When
        val result = repository.getList().first()

        // Then
        assertTrue(result is Resource.Empty)
    }

    @Test
    fun `getList should return error resource when remote data source throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { remoteDataSource.getList() } throws exception

        // When
        val result = repository.getList().first()

        // Then
        assertTrue(result is Resource.Error)
        val errorResult = result as Resource.Error
        assertEquals("Network error", errorResult.message)
    }

    @Test
    fun `getList should return error resource when remote data source throws generic exception`() = runTest {
        // Given
        val exception = Exception("Generic error")
        coEvery { remoteDataSource.getList() } throws exception

        // When
        val result = repository.getList().first()

        // Then
        assertTrue(result is Resource.Error)
        val errorResult = result as Resource.Error
        assertEquals("Generic error", errorResult.message)
    }
}
