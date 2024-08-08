package com.op.movies.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.op.movies.R

fun ImageView.loadImage(path: String, context: Context) {
    Glide
        .with(context)
        .load(path)
        .centerCrop()
        .placeholder(R.drawable.bg_image)
        .into(this)
}

fun ImageView.loadImage(uri: Uri, context: Context) {
    Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.drawable.bg_image)
        .into(this)
}