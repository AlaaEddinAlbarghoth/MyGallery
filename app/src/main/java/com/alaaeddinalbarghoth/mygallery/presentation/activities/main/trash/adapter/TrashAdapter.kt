package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.trash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.databinding.ItemTrashBinding
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed.adapter.FeedsDiffUtil

class TrashAdapter : ListAdapter<FeedItem, TrashAdapter.TrashVH>(FeedsDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TrashAdapter.TrashVH =
        TrashVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_trash,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TrashAdapter.TrashVH, position: Int) {
        with(holder.itemTrashBinding) {
            this.feedItem = getItem(position)
        }
    }

    /** ViewHolder */
    inner class TrashVH(val itemTrashBinding: ItemTrashBinding) :
        RecyclerView.ViewHolder(itemTrashBinding.root)
}