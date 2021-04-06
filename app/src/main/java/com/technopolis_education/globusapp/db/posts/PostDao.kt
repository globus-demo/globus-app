package com.technopolis_education.globusapp.db.posts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getAll(): List<Post>

    @Query("SELECT * FROM post WHERE pid IN (:postIds)")
    suspend fun loadAllByIds(postIds: IntArray): List<Post>

    @Query("SELECT * FROM post WHERE title LIKE :title LIMIT 1")
    suspend fun findByName(title: String): Post

    @Insert
    suspend fun insertAll(vararg posts: Post)

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post: Post)
}