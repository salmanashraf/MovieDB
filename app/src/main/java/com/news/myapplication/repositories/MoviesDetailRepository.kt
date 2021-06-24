package com.news.myapplication.repositories

import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.network.MainApiClient
import com.news.myapplication.output.SimpleOutput
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception

class MoviesDetailRepository private constructor() {

    var output = SimpleOutput<MovieDetails, String>()

    companion object {
        private var mInstance: MoviesDetailRepository? = null
        fun getInstance(): MoviesDetailRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = MoviesDetailRepository()
                }
            }
            return mInstance!!
        }
    }

    fun getMovies(movieDisposable: CompositeDisposable, movieID: Int) {
        try {
            movieDisposable.add(
                MainApiClient.getMoviesApi()
                    .getMovieDetails(movieID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { getMovieDetails ->
                            completed(getMovieDetails)
                        }, {
                            Timber.d(it)
                        })
            )

        } catch (e: Exception) {
            Timber.d(e.message.toString())
        }
    }

    private fun completed(movieDetail: MovieDetails) {
        output.complete.postValue(movieDetail)
    }

}
