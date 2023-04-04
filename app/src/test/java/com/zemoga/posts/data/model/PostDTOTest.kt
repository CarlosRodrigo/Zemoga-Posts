package com.zemoga.posts.data.model

import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.data.entities.remote.PostDTO
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PostDTOTest {

    private val postDto = PostDTO(
        userId = 1,
        id = 1,
        title = "Post title",
        body = "Post description"
    )

    @Test
    fun should_ConvertFromDTO_ToModel() {
        val post: Post = postDto.toModel()

        assertTrue(post is Post)
        assertTrue(post.userId == postDto.userId)
        assertTrue(post.id == postDto.id)
        assertTrue(post.title == postDto.title)
    }
}