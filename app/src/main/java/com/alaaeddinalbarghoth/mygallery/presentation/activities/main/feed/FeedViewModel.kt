package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaaeddinalbarghoth.mygallery.domain.usecase.FeedUseCase
import com.alaaeddinalbarghoth.mygallery.utils.FeedsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedUseCase: FeedUseCase
) : ViewModel() {

    private val _feedsList = MutableLiveData<FeedsList>()
    val feedsList: LiveData<FeedsList> = _feedsList

    init {
        getFeedsList()
    }

    fun getFeedsList() {
        viewModelScope.launch {
            _feedsList.value = feedUseCase.getFeeds()
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _feedsList.value = feedUseCase.search(query)
        }
    }
}