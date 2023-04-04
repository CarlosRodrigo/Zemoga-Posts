package com.zemoga.posts.domain.usecases

import com.zemoga.posts.core.Resource
import com.zemoga.posts.core.UseCase
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.data.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsUseCase(private val repository: PostsRepository) :
    UseCase.NoParam<Resource<List<Post>>>() {

    override suspend fun execute(): Flow<Resource<List<Post>>> = repository.listPosts()
}