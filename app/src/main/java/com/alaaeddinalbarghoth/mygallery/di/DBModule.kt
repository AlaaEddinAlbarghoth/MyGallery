package com.alaaeddinalbarghoth.mygallery.di

import android.content.Context
import com.alaaeddinalbarghoth.mygallery.data.dao.FeedDao
import com.alaaeddinalbarghoth.mygallery.data.db.FeedRoomDatabase
import com.alaaeddinalbarghoth.mygallery.domain.repositories.FeedRepository
import com.alaaeddinalbarghoth.mygallery.data.repositories.FeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideFeedDao(@ApplicationContext context: Context): FeedDao =
        FeedRoomDatabase.getDatabase(context).feedDao()
}