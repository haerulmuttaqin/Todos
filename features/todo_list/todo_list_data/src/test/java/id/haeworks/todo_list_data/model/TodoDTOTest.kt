package id.haeworks.todo_list_data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TodoDTOTest {

    @Test
    fun `TodoDTO should be created with all properties`() {
        // Given & When
        val todoDTO = TodoDTO(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )

        // Then
        assertEquals(1, todoDTO.id)
        assertEquals("Test Todo", todoDTO.title)
        assertEquals(123, todoDTO.userId)
        assertEquals(true, todoDTO.completed)
    }

    @Test
    fun `TodoDTO should be created with null values`() {
        // Given & When
        val todoDTO = TodoDTO(
            id = null,
            title = null,
            userId = null,
            completed = null
        )

        // Then
        assertNull(todoDTO.id)
        assertNull(todoDTO.title)
        assertNull(todoDTO.userId)
        assertNull(todoDTO.completed)
    }

    @Test
    fun `TodoDTO should be created with mixed null and non-null values`() {
        // Given & When
        val todoDTO = TodoDTO(
            id = 5,
            title = "Partial Todo",
            userId = null,
            completed = false
        )

        // Then
        assertEquals(5, todoDTO.id)
        assertEquals("Partial Todo", todoDTO.title)
        assertNull(todoDTO.userId)
        assertEquals(false, todoDTO.completed)
    }

    @Test
    fun `TodoDTO should support equality comparison`() {
        // Given
        val todoDTO1 = TodoDTO(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )
        val todoDTO2 = TodoDTO(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )
        val todoDTO3 = TodoDTO(
            id = 2,
            title = "Different Todo",
            userId = 456,
            completed = false
        )

        // Then
        assertEquals(todoDTO1, todoDTO2)
        assertEquals(todoDTO1.hashCode(), todoDTO2.hashCode())
        assert(todoDTO1 != todoDTO3)
    }

    @Test
    fun `TodoDTO should support toString method`() {
        // Given
        val todoDTO = TodoDTO(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )

        // When
        val toString = todoDTO.toString()

        // Then
        assert(toString.contains("id=1"))
        assert(toString.contains("title=Test Todo"))
        assert(toString.contains("userId=123"))
        assert(toString.contains("completed=true"))
    }

    @Test
    fun `TodoDTO should support copy method`() {
        // Given
        val originalTodo = TodoDTO(
            id = 1,
            title = "Original Todo",
            userId = 123,
            completed = false
        )

        // When
        val copiedTodo = originalTodo.copy(
            title = "Modified Todo",
            completed = true
        )

        // Then
        assertEquals(1, copiedTodo.id)
        assertEquals("Modified Todo", copiedTodo.title)
        assertEquals(123, copiedTodo.userId)
        assertEquals(true, copiedTodo.completed)
        
        // Original should remain unchanged
        assertEquals("Original Todo", originalTodo.title)
        assertEquals(false, originalTodo.completed)
    }
}
