package reprator.wipro.base.util.network

import reprator.wipro.base.useCases.AppError
import reprator.wipro.base.useCases.AppResult

suspend fun <T : Any> safeApiCall(
    call: suspend () -> AppResult<T>,
    errorMessage: String? = null
): AppResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        println(e.printStackTrace())
        AppError(message = errorMessage ?: e.message)
    }
}