package com.news.myapplication.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.news.myapplication.R
import com.news.myapplication.ui.moviedetail.MovieDetailActivity
import com.news.myapplication.ui.moviedetail.MovieDetailActivity.Companion.REQUEST_MOVIE_ID
import com.news.myapplication.ui.movies.adapters.MovieListAdapter
import com.news.myapplication.utils.NetworkHelper
import com.news.myapplication.utils.visible
import com.news.myapplication.viewmodels.MoviesDetailViewModel
import com.news.myapplication.viewmodels.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.activity_movies.view.*

class MoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel
    private var movieAdapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        observeData()

        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
           observeData()
        })
    }


    /**
     *
     */
    private fun observeData() {
        setUpRecyclerView()

        viewModel.moviePagedList.observe(this, {
            movieAdapter.submitList(it)
            swipeContainer.isRefreshing = false;
        })

        viewModel.output.loading.observe(this, {
            progress_circular.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.output.error.observe(this, {
            error.text = it
            it?.let {error.visible() }
            swipeContainer.isRefreshing = false;
        })

        if (!NetworkHelper.isOnline(this))
            Toast.makeText(this, getString(R.string.error_internet), Toast.LENGTH_SHORT)
                .show()
    }

    private fun setUpRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.layoutManager = gridLayoutManager
        moviesRecyclerView.setHasFixedSize(true)
        moviesRecyclerView.adapter = movieAdapter
        movieAdapter.onMovieClicked = {
            it?.let { movie ->
                openMovieDetails(movie.id)
            }
        }
    }

    private fun openMovieDetails(movieId: Int) {
        let {
            it.startActivity(
                Intent(it, MovieDetailActivity::class.java)
                    .putExtra(REQUEST_MOVIE_ID, movieId)
            )
        }
    }
}
