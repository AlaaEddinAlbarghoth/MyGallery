package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.databinding.ItemFeedBinding

class FeedsAdapter :
    ListAdapter<FeedItem, FeedsAdapter.FeedsVH>(FeedsDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FeedsAdapter.FeedsVH =
        FeedsVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_feed,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        with(holder.itemFeedBinding) {
            this.feedItem = getItem(position)

            holder.itemFeedBinding.ivShare.setOnClickListener {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "image/*"
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getItem(position).imageUri))
                this.ivShare.context.startActivities(
                    arrayOf(
                        Intent.createChooser(
                            sharingIntent,
                            this.ivShare.context.getString(R.string.share_with)
                        )
                    )
                )
            }

        }
    }

    /** ViewHolder */
    inner class FeedsVH(val itemFeedBinding: ItemFeedBinding) :
        RecyclerView.ViewHolder(itemFeedBinding.root)
}