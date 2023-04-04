package com.zemoga.posts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zemoga.posts.data.dao.PostDao
import com.zemoga.posts.data.database.PostDatabase
import com.zemoga.posts.data.entities.db.PostDb
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostDatabaseTest {

    private lateinit var dbPosts: List<PostDb>

    private lateinit var dao: PostDao
    private lateinit var postDatabase: PostDatabase

    @Before
    fun createPostsForTest() {
        val post1 = PostDb(
            userId = 1,
            id = 1,
            title = "Post title",
            body = "Post description"
        )

        val post2 = PostDb(
            userId = 1,
            id = 2,
            title = "Post title 2",
            body = "Post description 2"
        )

        dbPosts = listOf(post1, post2)
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        postDatabase = Room.inMemoryDatabaseBuilder(
            context,
            PostDatabase::class.java
        ).build()
        dao = postDatabase.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        postDatabase.close()
    }

    @Test
    fun should_StorePostsInDatabase_WhenReceiveListOfPosts() {
        lateinit var result: List<PostDb>
        runBlocking {
            result = dao.listPosts().first()
        }
        assertTrue(result.isEmpty())

        runBlocking {
            dao.saveAll(dbPosts)
            result = dao.listPosts().first()
        }
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun should_ReturnPosts_WhenReadingFromDatabase() {
        lateinit var result: PostDb

        runBlocking {
            dao.saveAll(dbPosts)
            result = dao.listPosts().first()[0]
        }
        assertTrue(result.title == dbPosts[0].title)
    }

    @Test
    fun should_ClearDatabase_WhenCallingClearDb() {
        lateinit var result: List<PostDb>

        runBlocking {
            dao.saveAll(dbPosts)
            dao.clearPosts()
            result = dao.listPosts().first()
        }
        assertTrue(result.isEmpty())
    }
}