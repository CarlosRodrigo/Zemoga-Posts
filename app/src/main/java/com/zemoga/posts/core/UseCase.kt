package com.zemoga.posts.core

import kotlinx.coroutines.flow.Flow

/**
 * Base class for a UseCase.
 * A UseCase handles business logic and lives in the domain layer.
 * A UseCase should only perform one small task on the execute method.
 */
abstract class UseCase<Param, Source> {

    abstract suspend fun execute(param: Param): Flow<Source>

    suspend operator fun invoke(param: Param) = execute(param)

    abstract class NoParam<Source>: UseCase<Nothing, Source>() {

        abstract suspend fun execute(): Flow<Source>

        suspend operator fun invoke() = execute()

        final override suspend fun execute(param: Nothing): Flow<Source> {
            throw UnsupportedOperationException()
        }
    }

}