package com.news.myapplication.repositories.movies

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.output.SimpleOutput
import com.news.myapplication.models.movies.MovieModel
import io.reactivex.disposables.CompositeDisposable

class MoviesRepository() {

    var output = SimpleOutput<MovieDetails, String>()
    private lateinit var movieModelPagedList: LiveData<PagedList<MovieModel>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    companion object {
        private var mInstance: MoviesRepository? = null
        fun getInstance(): MoviesRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = MoviesRepository()
                }
            }
            return mInstance!!
        }
    }


    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<MovieModel>> {
        moviesDataSourceFactory = MovieDataSourceFactory(compositeDisposable)
        output = moviesDataSourceFactory.output

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        movieModelPagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return movieModelPagedList
    }
}