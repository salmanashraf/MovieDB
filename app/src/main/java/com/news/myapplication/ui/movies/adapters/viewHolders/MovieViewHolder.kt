package com.news.myapplication.ui.movies.adapters.viewHolders

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.news.myapplication.BuildConfig
import com.news.myapplication.R
import com.news.myapplication.models.movies.MovieModel
import kotlinx.android.synthetic.main.movie_items.view.*

class MovieViewHolder(itemView: View, onItemClicked: ((MovieModel?) -> Unit)) :
    BaseViewHolder(itemView) {

    private lateinit var iMoviePoster: ImageView
    private lateinit var progressBar: CircularProgressBar
    private lateinit var movieTitle: TextView
    private lateinit var moviePercentage: TextView
    private var mItemView = itemView
    private var movieClick = onItemClicked

    init {
        findViews(itemView, onItemClicked)
    }

    private fun findViews(itemView: View, onMovieClicked: ((MovieModel?) -> Unit)) {
        iMoviePoster = itemView.findViewById(R.id.image)
        progressBar = itemView.findViewById(R.id.circularProgressBar)
        movieTitle = itemView.findViewById(R.id.movietitle)
        moviePercentage = itemView.findViewById(R.id.percentage)


    }

    fun setMovie(movieModel: MovieModel) {
        movieTitle.text = movieModel.title

        val voteAverage: Double = movieModel.voteAverage
        itemView.circularProgressBar.progress = voteAverage.times(10).toFloat()
        itemView.percentage.text = String.format("%d%s", voteAverage.times(10).toInt(), "%")

        Glide.with(getContext())
            .load("${BuildConfig.IMAGE_URL}${movieModel.posterPath}")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(iMoviePoster)


        mItemView.setOnClickListener {
            movieClick?.invoke(movieModel)
        }

    }
}