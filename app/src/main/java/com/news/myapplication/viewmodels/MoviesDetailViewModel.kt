package com.news.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.output.SimpleOutput
import com.news.myapplication.repositories.MoviesDetailRepository
import com.news.myapplication.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class MoviesDetailViewModel(private val app: Application) : AndroidViewModel(app) {
    var output = SimpleOutput<MovieDetails, String>()
    private val compositeDisposable = CompositeDisposable()

    private val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()

    private var mRepository = MoviesDetailRepository.getInstance()

    fun fetchMovieDetail(movieID: Int){
        if (NetworkHelper.isOnline(app.baseContext)) {
            var mRepository = MoviesDetailRepository.getInstance()
            mRepository.getMovies(compositeDisposable, movieID)
        } else {
            mShowNetworkError.value = true
        }
    }


    init {
        output = mRepository.output
    }

}