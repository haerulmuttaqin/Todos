package id.haeworks.todo_list_domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TodoTest {

    @Test
    fun `Todo should be created with all properties`() {
        // Given & When
        val todo = Todo(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )

        // Then
        assertEquals(1, todo.id)
        assertEquals("Test Todo", todo.title)
        assertEquals(123, todo.userId)
        assertEquals(true, todo.completed)
    }

    @Test
    fun `Todo should be created with false completion status`() {
        // Given & When
        val todo = Todo(
            id = 2,
            title = "Incomplete Todo",
            userId = 456,
            completed = false
        )

        // Then
        assertEquals(2, todo.id)
        assertEquals("Incomplete Todo", todo.title)
        assertEquals(456, todo.userId)
        assertEquals(false, todo.completed)
    }

    @Test
    fun `Todo should support equality comparison`() {
        // Given
        val todo1 = Todo(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )
        val todo2 = Todo(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )
        val todo3 = Todo(
            id = 2,
            title = "Different Todo",
            userId = 456,
            completed = false
        )

        // Then
        assertEquals(todo1, todo2)
        assertEquals(todo1.hashCode(), todo2.hashCode())
        assert(todo1 != todo3)
    }

    @Test
    fun `Todo should support toString method`() {
        // Given
        val todo = Todo(
            id = 1,
            title = "Test Todo",
            userId = 123,
            completed = true
        )

        // When
        val toString = todo.toString()

        // Then
        assert(toString.contains("id=1"))
        assert(toString.contains("title=Test Todo"))
        assert(toString.contains("userId=123"))
        assert(toString.contains("completed=true"))
    }

    @Test
    fun `Todo should support copy method`() {
        // Given
        val originalTodo = Todo(
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

    @Test
    fun `Todo should support copy method with all properties`() {
        // Given
        val originalTodo = Todo(
            id = 1,
            title = "Original Todo",
            userId = 123,
            completed = false
        )

        // When
        val copiedTodo = originalTodo.copy(
            id = 2,
            title = "New Todo",
            userId = 456,
            completed = true
        )

        // Then
        assertEquals(2, copiedTodo.id)
        assertEquals("New Todo", copiedTodo.title)
        assertEquals(456, copiedTodo.userId)
        assertEquals(true, copiedTodo.completed)
        
        // Original should remain unchanged
        assertEquals(1, originalTodo.id)
        assertEquals("Original Todo", originalTodo.title)
        assertEquals(123, originalTodo.userId)
        assertEquals(false, originalTodo.completed)
    }

    @Test
    fun `Todo should handle edge cases`() {
        // Given & When
        val todoWithZeroId = Todo(
            id = 0,
            title = "",
            userId = 0,
            completed = false
        )

        // Then
        assertEquals(0, todoWithZeroId.id)
        assertEquals("", todoWithZeroId.title)
        assertEquals(0, todoWithZeroId.userId)
        assertEquals(false, todoWithZeroId.completed)
    }
}
