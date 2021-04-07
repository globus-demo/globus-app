package com.technopolis_education.globusapp.db.posts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey(autoGenerate = true) val pid: Int,
    @ColumnInfo(name = "creator") val user: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "likes") val likes: Int?,
)