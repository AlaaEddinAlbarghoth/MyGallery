package com.alaaeddinalbarghoth.mygallery.utils.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", requireAll = true)
fun ImageView.setImageUrl(
    imageUrl: String?,
) {
    imageUrl?.let {
        Glide
            .with(this.context)
            .load(imageUrl)
            .into(this)
    }
}