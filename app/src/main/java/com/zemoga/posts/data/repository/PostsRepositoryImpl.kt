package com.zemoga.posts.data.repository

import com.zemoga.posts.core.CustomException
import com.zemoga.posts.core.Resource
import com.zemoga.posts.core.networkBoundResource
import com.zemoga.posts.data.dao.PostDao
import com.zemoga.posts.data.entities.db.toModel
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.data.entities.remote.toDb
import com.zemoga.posts.data.remote.PostsAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PostsRepositoryImpl(
    private val service: PostsAPI,
    private val dao: PostDao
) : PostsRepository {

    override suspend fun listPosts(): Flow<Resource<List<Post>>> =
        networkBoundResource(
            query = {
                dao.listPosts().map {
                    it.toModel()
                }
            },
            fetch = { service.getPosts() },
            saveFetchResult = { postsDTO ->
                dao.clearPosts()
                dao.saveAll(postsDTO.toDb())
            },
            onError = {
                CustomException("Could not connect to the API. Displaying cached content.")
            })

    override suspend fun getPostComments(postId: Int): Flow<Resource<List<Comment>>> =
        networkBoundResource(
            query = {
                dao.listComments(postId).map {
                    it.toModel()
                }
            },
            fetch = { service.getCommentsByPostId(postId) },
            saveFetchResult = { commentsDTO ->
                dao.clearComments(postId)
                dao.saveComments(commentsDTO.toDb())
            },
            onError = {
                CustomException("Could not connect to the API. Displaying cached content.")
            })

    override suspend fun favoritePost(postId: Int): Flow<Resource<List<Post>>> = flow {
        val postDb = dao.getPost(postId).first()
        postDb.favorite = 1
        dao.save(postDb)

        val posts = dao.listPosts().first()

        emit(Resource.Success(posts.toModel()))
    }

    override suspend fun unFavoritePost(postId: Int): Flow<Resource<List<Post>>> = flow {
        val postDb = dao.getPost(postId).first()
        postDb.favorite = 0
        dao.save(postDb)

        val posts = dao.listPosts().first()

        emit(Resource.Success(posts.toModel()))
    }

    override suspend fun removePost(postId: Int): Flow<Resource<Unit>> = flow {
        dao.removePost(postId)

        emit(Resource.Success(Unit))
    }

    override suspend fun removeNotFavoritePosts(): Flow<Resource<List<Post>>> = flow {
        dao.removeNotFavoritePosts()

        val posts = dao.listPosts().first()

        emit(Resource.Success(posts.toModel()))
    }

}