package com.technopolis_education.globusapp.db.posts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): List<Post>

    @Query("SELECT * FROM post WHERE pid IN (:postIds)")
    fun loadAllByIds(postIds: IntArray): List<Post>

    @Query("SELECT * FROM post WHERE title LIKE :title LIMIT 1")
    fun findByName(title: String): Post

    @Insert
    fun insertAll(vararg posts: Post)

    @Update
    fun update(post: Post)

    @Delete
    fun delete(post: Post)
}