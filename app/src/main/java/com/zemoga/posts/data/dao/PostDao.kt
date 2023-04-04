package com.zemoga.posts.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zemoga.posts.data.entities.db.CommentDb
import com.zemoga.posts.data.entities.db.PostDb
import kotlinx.coroutines.flow.Flow

/**
 * Interface for Room post database.
 */
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(list: List<PostDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(postDb: PostDb)

    @Query("SELECT * FROM post ORDER BY favorite DESC")
    fun listPosts(): Flow<List<PostDb>>

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPost(postId: Int): Flow<PostDb>

    @Query("DELETE FROM post WHERE favorite = 0")
    suspend fun removeNotFavoritePosts()

    @Query("DELETE FROM post WHERE id = :postId")
    suspend fun removePost(postId: Int)

    @Query("DELETE FROM post")
    suspend fun clearPosts()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveComments(list: List<CommentDb>)

    @Query("SELECT * FROM comment WHERE postId = :postId ")
    fun listComments(postId: Int): Flow<List<CommentDb>>

    @Query("DELETE FROM comment WHERE postId = :postId")
    suspend fun clearComments(postId: Int)
}