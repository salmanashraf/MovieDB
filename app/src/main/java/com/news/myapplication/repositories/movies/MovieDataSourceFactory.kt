package com.news.myapplication.repositories.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.output.SimpleOutput
import com.news.myapplication.models.movies.MovieModel
import com.news.myapplication.network.ApiServices
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, MovieModel>() {

    private val moviesLiveDataSource = MutableLiveData<MovieDataSource>()
    var output = SimpleOutput<MovieDetails, String>()

    override fun create(): DataSource<Int, MovieModel> {
        val movieDataSource = MovieDataSource(compositeDisposable)
        output = movieDataSource.output
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}