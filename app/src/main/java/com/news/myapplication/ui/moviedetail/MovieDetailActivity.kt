package com.news.myapplication.ui.moviedetail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.c.progress_dialog.BlackProgressDialog
import com.news.myapplication.BuildConfig
import com.news.myapplication.R
import com.news.myapplication.models.movieDetails.Genre
import com.news.myapplication.models.movieDetails.Language
import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.utils.loadImage
import com.news.myapplication.viewmodels.MoviesDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_items.view.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_MOVIE_ID = "MovieId"
    }

    val weburl = "https://www.themoviedb.org/movie/"

    private lateinit var mViewModel: MoviesDetailViewModel
    private var movieID: Int = 0
    lateinit var movieDisposable: CompositeDisposable
    lateinit var dialog: BlackProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        dialog = BlackProgressDialog(this, getString(R.string.loading))
        dialog.setCancelable(false)
        movieDisposable = CompositeDisposable()
        movieID = intent.getIntExtra(REQUEST_MOVIE_ID, 0)
        mViewModel = ViewModelProvider(this).get(MoviesDetailViewModel::class.java)
        mViewModel.output.complete.observe(this, {
            setMovieData(it)
        })
        dialog.show()
        mViewModel.fetchMovieDetail(movieID)

    }


    private fun setMovieData(movieDetails: MovieDetails) {
        dialog.hide()
        title = movieDetails?.title
        movietitle.text = movieDetails?.title
        movie_release_date.text = movieDetails?.releaseDate
        moviedescription.text = movieDetails?.overview
        runtime.text = String.format("%s min", movieDetails?.runtime)
        image.loadImage("${BuildConfig.IMAGE_URL}${movieDetails.posterPath}")
        movieGenre.text = movieDetails.genres.genreToCommaSeparatedString()
        language.text = movieDetails.spokenLanguages.languageToCommaSeparatedString()

        bookNow.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("$weburl${movieID}")
            startActivity(openURL)
        }
    }


    fun List<Genre>.genreToCommaSeparatedString(): String {
        return this.joinToString(", ", transform = { it.name })
    }
    fun List<Language>.languageToCommaSeparatedString(): String {
        return this.joinToString(", ", transform = { it.name })
    }
}
