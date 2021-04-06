package com.technopolis_education.globusapp.db.posts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.technopolis_education.globusapp.db.users.User

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("user"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Post(
    @PrimaryKey(autoGenerate = true) val pid: Int,
    @ColumnInfo(name = "creator") val user: User,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val text: String?,
    @ColumnInfo(name = "likes") val likes: Int?,
)