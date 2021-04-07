package com.technopolis_education.globusapp.db.posts

import androidx.lifecycle.LiveData

class PostRepository(private val postDao: PostDao) {

    val readAllData: LiveData<List<Post>> = postDao.getAll()

    suspend fun addPost(post: Post){
        postDao.insertAll(post)
    }
}