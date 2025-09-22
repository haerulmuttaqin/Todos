package id.haeworks.todo_list_data.model


import com.google.gson.annotations.SerializedName

data class TodoDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("completed")
    val completed: Boolean?,
)