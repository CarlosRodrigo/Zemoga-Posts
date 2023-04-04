package com.zemoga.posts.domain.usecases

import com.zemoga.posts.core.Resource
import com.zemoga.posts.core.UseCase
import com.zemoga.posts.data.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class RemovePostUseCase(private val postsRepository: PostsRepository): UseCase<Int, Resource<Unit>>() {

    override suspend fun execute(param: Int): Flow<Resource<Unit>> {
        return postsRepository.removePost(param)
    }
}