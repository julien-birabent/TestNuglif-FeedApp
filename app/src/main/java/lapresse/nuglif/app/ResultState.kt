package lapresse.nuglif.app


sealed class ResultState<T> {
    data class Loading<T>(val resultData: T?) : ResultState<T>()
    data class Success<T>(val resultData: T) : ResultState<T>()
    data class Error<T>(val throwable: Throwable, val lastData: T?) : ResultState<T>()
}

val <T>ResultState<T>.data: T?
    get() = when (this) {
        is ResultState.Loading -> resultData
        is ResultState.Success -> resultData
        is ResultState.Error -> lastData
    }