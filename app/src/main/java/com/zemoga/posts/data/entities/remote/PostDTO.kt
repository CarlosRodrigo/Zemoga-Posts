package com.zemoga.posts.data.entities.remote

import com.squareup.moshi.Json
import com.zemoga.posts.data.entities.db.PostDb
import com.zemoga.posts.data.entities.model.Post

data class PostDTO(
    @Json(name = "userId") var userId: Int,
    @Json(name = "id") var id: Int,
    @Json(name = "title") var title: String,
    @Json(name = "body") var body: String
) {

    fun toModel(): Post =
        Post(
            userId = userId,
            id = id,
            title = title,
            body = body
        )

    fun toDb(): PostDb =
        PostDb(
            userId = userId,
            id = id,
            title = title,
            body = body
        )
}

fun List<PostDTO>.toModel(): List<Post> =
    this.map {
        it.toModel()
    }


fun List<PostDTO>.toDb(): List<PostDb> =
    this.map {
        it.toDb()
    }