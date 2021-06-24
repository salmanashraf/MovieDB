package com.news.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.models.movies.MovieModel
import com.news.myapplication.output.SimpleOutput
import com.news.myapplication.repositories.MoviesDetailRepository
import com.news.myapplication.repositories.movies.MoviesRepository
import io.reactivex.disposables.CompositeDisposable

/**
 *
 */
class MoviesViewModel(private val app: Application) : AndroidViewModel(app) {

    /**
     *
     */
    private val compositeDisposable = CompositeDisposable()
    var output = SimpleOutput<MovieDetails, String>()
    private var mRepository = MoviesRepository.getInstance()

    val moviePagedList: LiveData<PagedList<MovieModel>> by lazy {
        mRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    init {
        output = mRepository.output
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}