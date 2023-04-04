package com.zemoga.posts.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.posts.data.entities.model.Post

@Entity(tableName = "post")
data class PostDb(
    @PrimaryKey
    var id: Int,
    var userId: Int,
    var title: String,
    var body: String,
    var favorite: Int = 0
) {

    fun toModel(): Post = Post(
        userId = userId,
        id = id,
        title = title,
        body = body,
        favorite = favorite == 1
    )
}


fun List<PostDb>.toModel(): List<Post> =
    this.map {
        it.toModel()
    }