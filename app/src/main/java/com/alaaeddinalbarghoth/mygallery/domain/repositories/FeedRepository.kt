package com.alaaeddinalbarghoth.mygallery.domain.repositories

import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem

interface FeedRepository {

    suspend fun getFeeds(): List<FeedItem>

    suspend fun getDeletedFeeds(): List<FeedItem>

    suspend fun search(query: String): List<FeedItem>

    suspend fun addFeedItem(feedItem: FeedItem)

    suspend fun updateFeedItem(id: Int)
}