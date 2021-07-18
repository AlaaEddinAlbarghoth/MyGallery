package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem

class FeedsDiffUtil : DiffUtil.ItemCallback<FeedItem>() {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
        oldItem.title === newItem.title

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
        oldItem == newItem
}