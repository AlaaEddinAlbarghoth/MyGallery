package com.alaaeddinalbarghoth.mygallery.domain.usecase

import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.domain.repositories.FeedRepository
import javax.inject.Inject

class FeedUseCase @Inject constructor(private val feedRepository: FeedRepository) {

    suspend fun getFeeds() =
        feedRepository.getFeeds()

    suspend fun getDeletedFeeds() =
        feedRepository.getDeletedFeeds()

    suspend fun search(query: String): List<FeedItem> =
        feedRepository.search(query)

    suspend fun addFeedItem(feedItem: FeedItem) =
        feedRepository.addFeedItem(feedItem)

}