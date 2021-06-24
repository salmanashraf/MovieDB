package com.news.myapplication.ui.movies.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.news.myapplication.BuildConfig
import com.news.myapplication.R
import com.news.myapplication.utils.loadImage
import com.news.myapplication.models.movies.MovieModel
import kotlinx.android.synthetic.main.movie_items.view.*

class MovieListAdapter : PagedListAdapter<MovieModel, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    var onMovieClicked: ((MovieModel?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_items, parent, false)
        return MovieItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).bind(getItem(position))
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            (oldItem == newItem)
    }


    inner class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: MovieModel?) {
            itemView.title.text = movie?.title
            itemView.release_date.text = movie?.releaseDate
            val voteAverage: Double? = movie?.voteAverage
            itemView.circularProgressBar.progress = voteAverage?.times(10)?.toFloat()!!
            itemView.percentage.text = String.format("%d%s", voteAverage.times(10).toInt(), "%")
            itemView.image.loadImage("${BuildConfig.IMAGE_URL}${movie.posterPath}")
            itemView.setOnClickListener {
                onMovieClicked?.invoke(movie)
            }
        }
    }
}