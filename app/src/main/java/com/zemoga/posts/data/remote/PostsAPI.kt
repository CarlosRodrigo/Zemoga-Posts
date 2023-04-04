package com.zemoga.posts.data.remote

import com.zemoga.posts.data.entities.remote.CommentDTO
import com.zemoga.posts.data.entities.remote.PostDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface responsible for Posts API communication
 */
interface PostsAPI {

    /**
     * Endpoint to get posts
     */
    @GET("posts")
    suspend fun getPosts() : List<PostDTO>

    /**
     * Endpoint to get comments from a post
     */
    @GET("posts/{postId}/comments")
    suspend fun getCommentsByPostId(@Path("postId") postId: Int) : List<CommentDTO>
}