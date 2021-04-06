package com.technopolis_education.globusapp.db.posts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true) val pid: Int,
    @ColumnInfo(name = "creator") val user: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val text: String?,
    @ColumnInfo(name = "likes") val likes: Int?,
)