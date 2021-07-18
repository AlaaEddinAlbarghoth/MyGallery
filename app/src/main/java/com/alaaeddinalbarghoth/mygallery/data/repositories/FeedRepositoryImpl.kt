package com.alaaeddinalbarghoth.mygallery.data.repositories

import androidx.annotation.WorkerThread
import com.alaaeddinalbarghoth.mygallery.data.dao.FeedDao
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.domain.repositories.FeedRepository
import com.alaaeddinalbarghoth.mygallery.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

class FeedRepositoryImpl(
    private val feedDao: FeedDao,
    private val dispatcher: DispatcherProvider
) : FeedRepository {

    override suspend fun getFeeds(): List<FeedItem> =
        withContext(dispatcher.io) {
            feedDao.getFeeds()
        }

    override suspend fun search(query: String): List<FeedItem> =
        withContext(dispatcher.io) {
            feedDao.search(query)
        }

    override suspend fun getDeletedFeeds(): List<FeedItem> =
        withContext(dispatcher.io) {
            feedDao.getDeletedFeeds()
        }

    @WorkerThread
    override suspend fun addFeedItem(feedItem: FeedItem) =
        withContext(dispatcher.io) {
            feedDao.insert(feedItem)
        }

    @WorkerThread
    override suspend fun updateFeedItem(id: Int) {

    }
}