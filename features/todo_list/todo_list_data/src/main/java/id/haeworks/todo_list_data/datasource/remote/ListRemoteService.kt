package id.haeworks.todo_list_data.datasource.remote

import id.haeworks.core.constant.Endpoints
import id.haeworks.todo_list_data.model.TodoDTO
import retrofit2.http.GET

@JvmSuppressWildcards
interface ListRemoteService {

    @GET(Endpoints.TODO_LIST)
    suspend fun getList(): List<TodoDTO>
}
