package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.trash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaaeddinalbarghoth.mygallery.domain.usecase.FeedUseCase
import com.alaaeddinalbarghoth.mygallery.utils.FeedsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val feedUseCase: FeedUseCase
) : ViewModel() {

    private val _feedsList = MutableLiveData<FeedsList>()
    val feedsList: LiveData<FeedsList> = _feedsList

    init {
        getDeletedFeeds()
    }

    fun getDeletedFeeds() {
        viewModelScope.launch {
            _feedsList.value = feedUseCase.getDeletedFeeds()
        }
    }

    fun remove(position: Int) {
        viewModelScope.launch {
            delay(100)
            _feedsList.value = feedUseCase.getDeletedFeeds()
        }
    }

}