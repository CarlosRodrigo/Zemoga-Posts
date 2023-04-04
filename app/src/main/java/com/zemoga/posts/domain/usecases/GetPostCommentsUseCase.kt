package com.zemoga.posts.domain.usecases

import com.zemoga.posts.core.Resource
import com.zemoga.posts.core.UseCase
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.data.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetPostCommentsUseCase(private val postsRepository: PostsRepository) :
    UseCase<Int, Resource<List<Comment>>>() {

    override suspend fun execute(param: Int): Flow<Resource<List<Comment>>> =
        postsRepository.getPostComments(param)
}