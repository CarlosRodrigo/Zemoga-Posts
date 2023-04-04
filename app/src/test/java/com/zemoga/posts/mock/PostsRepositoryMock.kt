package com.zemoga.posts.mock

import com.zemoga.posts.core.Resource
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.data.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostsRepositoryMock: PostsRepository {

    private val post1 = Post(
        userId = 1,
        id = 1,
        title = "Post title",
        body = "Post description"
    )
    private val post2 = Post(
        userId = 1,
        id = 2,
        title = "Post title 2",
        body = "Post description 2"
    )
    private val posts = mutableListOf(post1, post2)

    private val comment1 = Comment(
        postId = 1,
        id = 1,
        name = "Comment 1",
        body = "Description 1"
    )
    private val comment2 = Comment(
        postId = 1,
        id = 2,
        name = "Comment 1",
        body = "Description 1"
    )
    private val comments = mutableListOf(comment1, comment2)

    override suspend fun listPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Success(posts))
    }

    override suspend fun getPostComments(postId: Int): Flow<Resource<List<Comment>>> = flow {
        emit(Resource.Success(comments))
    }

    override suspend fun favoritePost(postId: Int): Flow<Resource<List<Post>>> = flow {
        post1.favorite = true
        emit(Resource.Success(posts))
    }

    override suspend fun unFavoritePost(postId: Int): Flow<Resource<List<Post>>> = flow {
        post1.favorite = false
        emit(Resource.Success(posts))
    }

    override suspend fun removePost(postId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Success(Unit))
    }

    override suspend fun removeNotFavoritePosts(): Flow<Resource<List<Post>>> = flow {
        posts.removeAt(0)
        emit(Resource.Success(posts))
    }
}