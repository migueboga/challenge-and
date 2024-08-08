package com.op.movies.presentation.gallery

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.op.movies.databinding.ItemProfileImageBinding
import com.op.movies.util.loadImage

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private val list: ArrayList<Uri> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<Uri>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemProfileImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val uri = list[position]
        holder.binding.itemProfileImageView.loadImage(uri, holder.itemView.context)
    }

    inner class GalleryViewHolder(
        val binding: ItemProfileImageBinding
    ): RecyclerView.ViewHolder(binding.root)
}