package id.haeworks.todo_detail_data.datasource.remote

import id.haeworks.core.constant.Endpoints
import id.haeworks.todo_detail_data.model.TodoDTO
import retrofit2.http.GET
import retrofit2.http.Path

@JvmSuppressWildcards
interface DetailRemoteService {

    @GET(Endpoints.TODO_DETAIL)
    suspend fun getDetail(
        @Path("id") id: Int,
    ): TodoDTO?
}
