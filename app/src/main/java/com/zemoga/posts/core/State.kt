package com.zemoga.posts.core

/**
 * Class used to represent the State of a UseCase execution.
 * Loading used to handle loading state
 * Success used after the execution returned successfully
 * Error used when an error occurred during execution
 */
sealed class State<out T: Any> {

    object Loading : State<Nothing>()

    data class Success<out T: Any>(val result: T) : State<T>()

    data class Error(val error: Throwable) : State<Nothing>()

}