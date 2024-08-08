package com.op.movies.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.op.movies.databinding.ItemMovieBinding
import com.op.movies.util.loadImage

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val list: ArrayList<MovieUiState> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<MovieUiState>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            val movie = list[position]
            binding.itemMovieTitle.text = movie.title
            binding.itemMovieImage.loadImage(movie.path, holder.itemView.context)
        }
    }

    inner class MovieViewHolder(
        val binding: ItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root)
}