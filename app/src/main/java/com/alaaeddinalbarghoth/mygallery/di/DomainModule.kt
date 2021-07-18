package com.alaaeddinalbarghoth.mygallery.di

import com.alaaeddinalbarghoth.mygallery.data.dao.FeedDao
import com.alaaeddinalbarghoth.mygallery.domain.repositories.FeedRepository
import com.alaaeddinalbarghoth.mygallery.data.repositories.FeedRepositoryImpl
import com.alaaeddinalbarghoth.mygallery.utils.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideFeedRepository(
        feedDao: FeedDao,
        dispatcherProvider: DispatcherProvider
    ): FeedRepository =
        FeedRepositoryImpl(feedDao, dispatcherProvider)
}