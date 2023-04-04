package com.zemoga.posts.data.repository

import com.zemoga.posts.core.Resource
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.data.entities.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun listPosts(): Flow<Resource<List<Post>>>

    suspend fun getPostComments(postId: Int): Flow<Resource<List<Comment>>>

    suspend fun favoritePost(postId: Int): Flow<Resource<List<Post>>>

    suspend fun unFavoritePost(postId: Int): Flow<Resource<List<Post>>>

    suspend fun removePost(postId: Int): Flow<Resource<Unit>>

    suspend fun removeNotFavoritePosts(): Flow<Resource<List<Post>>>
}