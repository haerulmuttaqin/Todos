package id.haeworks.core.network

import id.haeworks.core.enums.NetworkState

sealed class Resource<out T> constructor(
    val status: NetworkState,
    val message: String? = null,
    val data: T? = null
) {

    class Success<T>(data: T, message: String? = null) : Resource<T>(
        data = data,
        status = NetworkState.SUCCESS,
        message = message
    )

    class Error<T>(message: String?) : Resource<T>(
        status = NetworkState.ERROR,
        message = if (message?.contains("No address associated with hostname") == true) "Terjadi Kesalahan" else message
    )

    class Unauthorized<T>(message: String?) : Resource<T>(
        status = NetworkState.UNAUTHORIZED, message = message
    )

    class Empty<T> : Resource<T>(status = NetworkState.EMPTY)

}