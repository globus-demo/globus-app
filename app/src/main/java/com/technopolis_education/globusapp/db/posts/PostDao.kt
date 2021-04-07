package com.technopolis_education.globusapp.db.posts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {
    @Query("SELECT * FROM posts_table")
    fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM posts_table WHERE pid IN (:postIds)")
    fun loadAllByIds(postIds: IntArray): LiveData<List<Post>>

    @Query("SELECT * FROM posts_table WHERE title LIKE :title LIMIT 1")
    fun findByName(title: String): Post

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(post: Post)

    @Update
    fun update(post: Post)

    @Delete
    fun delete(post: Post)
}