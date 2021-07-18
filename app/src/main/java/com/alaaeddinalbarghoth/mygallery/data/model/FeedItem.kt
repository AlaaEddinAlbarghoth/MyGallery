package com.alaaeddinalbarghoth.mygallery.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_table")
data class FeedItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "deletedTime") val deletedTime: String? = null,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)