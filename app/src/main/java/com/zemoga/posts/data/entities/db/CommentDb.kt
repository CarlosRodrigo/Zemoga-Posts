package com.zemoga.posts.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.data.entities.model.Post

@Entity(tableName = "comment")
data class CommentDb(
    @PrimaryKey
    var id: Int,
    var postId: Int,
    var name: String,
    var body: String) {

    fun toModel(): Comment = Comment(
        postId = postId,
        id = id,
        name = name,
        body = body
    )
}


fun List<CommentDb>.toModel(): List<Comment> =
    this.map {
        it.toModel()
    }