package com.zemoga.posts.data.entities.model

data class Comment(
    var postId: Int,
    var id: Int,
    var name: String,
    var body: String)