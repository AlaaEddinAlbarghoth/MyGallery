package com.alaaeddinalbarghoth.mygallery.di

import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed.adapter.FeedsAdapter
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.trash.adapter.TrashAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object PresentationModule {

    @Provides
    fun provideFeedsAdapter(
    ): FeedsAdapter =
        FeedsAdapter()

    @Provides
    fun provideTrashAdapter(
    ): TrashAdapter =
        TrashAdapter()
}