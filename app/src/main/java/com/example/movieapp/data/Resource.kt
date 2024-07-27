package com.example.movieapp.data

sealed interface Resource<T : Any> {
    class Success<T: Any>(val data: T) : Resource<T>
    class Error<T: Any>(val code: Int, val message: String?) : Resource<T>
    class Exception<T: Any>(val e: Throwable) : Resource<T>
}
