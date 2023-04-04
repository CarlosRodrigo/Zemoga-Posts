package com.zemoga.posts.data.entities.remote

import com.squareup.moshi.Json
import com.zemoga.posts.data.entities.db.CommentDb
import com.zemoga.posts.data.entities.model.Comment

data class CommentDTO(
    @Json(name = "postId") var postId: Int,
    @Json(name = "id") var id: Int,
    @Json(name = "name") var name: String,
    @Json(name = "body") var body: String
) {

    fun toModel(): Comment =
        Comment(
            postId = postId,
            id = id,
            name = name,
            body = body
        )

    fun toDb(): CommentDb =
        CommentDb(
            postId = postId,
            id = id,
            name = name,
            body = body
        )
}

fun List<CommentDTO>.toModel(): List<Comment> =
    this.map {
        it.toModel()
    }

fun List<CommentDTO>.toDb(): List<CommentDb> =
    this.map {
        it.toDb()
    }