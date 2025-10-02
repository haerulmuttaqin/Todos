package id.haeworks.todo_list_data.mapper

import id.haeworks.todo_list_data.mapper.ListMapper.toDomainModel
import id.haeworks.todo_list_data.model.TodoDTO
import id.haeworks.todo_list_domain.model.Todo
import org.junit.Assert.assertEquals
import org.junit.Test

class ListMapperTest {

    @Test
    fun `toDomainModel should map TodoDTO to Todo correctly`() {
        // Given
        val todoDTO = TodoDTO(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )

        // When
        val result = todoDTO.toDomainModel()

        // Then
        assertEquals(Todo(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        ), result)
    }

    @Test
    fun `toDomainModel should handle null values with default values`() {
        // Given
        val todoDTO = TodoDTO(
            id = null,
            title = null,
            userId = null,
            completed = null
        )

        // When
        val result = todoDTO.toDomainModel()

        // Then
        assertEquals(Todo(
            id = 0,
            title = "",
            userId = 0,
            completed = false
        ), result)
    }

    @Test
    fun `toDomainModel should handle null TodoDTO`() {
        // Given
        val todoDTO: TodoDTO? = null

        // When
        val result = todoDTO.toDomainModel()

        // Then
        assertEquals(Todo(
            id = 0,
            title = "",
            userId = 0,
            completed = false
        ), result)
    }

    @Test
    fun `toDomainModel should handle partial null values`() {
        // Given
        val todoDTO = TodoDTO(
            id = 5,
            title = "Partial Todo",
            userId = null,
            completed = true
        )

        // When
        val result = todoDTO.toDomainModel()

        // Then
        assertEquals(Todo(
            id = 5,
            title = "Partial Todo",
            userId = 0,
            completed = true
        ), result)
    }
}
