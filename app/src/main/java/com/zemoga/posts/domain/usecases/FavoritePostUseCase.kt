package com.zemoga.posts.domain.usecases

import com.zemoga.posts.core.Resource
import com.zemoga.posts.core.UseCase
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.data.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class FavoritePostUseCase(private val repository: PostsRepository) :
    UseCase<Post, Resource<List<Post>>>() {

    override suspend fun execute(param: Post): Flow<Resource<List<Post>>> {
        return if (param.favorite == false) {
            repository.favoritePost(param.id)
        } else {
            repository.unFavoritePost(param.id)
        }
    }
}