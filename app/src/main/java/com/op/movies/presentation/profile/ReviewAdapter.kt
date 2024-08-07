package com.op.movies.presentation.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.op.movies.databinding.ItemReviewBinding
import com.op.movies.util.loadImage

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val list: ArrayList<ReviewUiState> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<ReviewUiState>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        with(holder) {
            val review = list[position]
            binding.itemReviewTitle.text = review.title
            binding.itemReviewOverview.text = review.overview
            binding.itemReviewVoteCount.text = review.voteCount
            binding.itemReviewImage.loadImage(review.posterPath, holder.itemView.context)
        }
    }


    override fun getItemCount(): Int = list.size



    inner class ReviewViewHolder(
        val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root)
}