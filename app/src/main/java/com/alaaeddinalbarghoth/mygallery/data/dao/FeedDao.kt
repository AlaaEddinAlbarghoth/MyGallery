package com.alaaeddinalbarghoth.mygallery.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed_table WHERE isDeleted = 0")
    fun getFeeds(): List<FeedItem>

    @Query("SELECT * FROM feed_table WHERE title LIKE :query AND description LIKE :query")
    fun search(query: String): List<FeedItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(feedItem: FeedItem)

    @Query("DELETE FROM feed_table WHERE id = :id")
    fun deleteFeedPermanently(id: Int)

    @Query("SELECT * FROM feed_table WHERE isDeleted = 1")
    fun getDeletedFeeds(): List<FeedItem>
}