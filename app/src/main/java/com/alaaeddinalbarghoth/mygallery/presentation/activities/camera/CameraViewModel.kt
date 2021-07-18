package com.alaaeddinalbarghoth.mygallery.presentation.activities.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.domain.usecase.FeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val feedUseCase: FeedUseCase
) : ViewModel() {

    fun addFeedItem(feedItem: FeedItem) =
        viewModelScope.launch {
            feedUseCase.addFeedItem(feedItem)
        }
}