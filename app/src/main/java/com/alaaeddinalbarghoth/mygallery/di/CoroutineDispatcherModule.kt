package com.alaaeddinalbarghoth.mygallery.di

import com.alaaeddinalbarghoth.mygallery.utils.dispatcher.CoroutineDispatcherProvider
import com.alaaeddinalbarghoth.mygallery.utils.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutineDispatcherModule {
    @Binds
    fun bindDispatcher(dispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider
}