package com.news.myapplication.repositories.movies

import androidx.paging.PageKeyedDataSource
import com.news.myapplication.FIRST_PAGE
import com.news.myapplication.models.movieDetails.MovieDetails
import com.news.myapplication.models.movies.MovieModel
import com.news.myapplication.network.ApiServices
import com.news.myapplication.network.MainApiClient
import com.news.myapplication.output.SimpleOutput
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MovieDataSource(
    private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, MovieModel>() {

    private var page = FIRST_PAGE

    var output = SimpleOutput<MovieDetails, String>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) {
        loading(true)
        compositeDisposable.add(
            MainApiClient.getMoviesApi()
            .getMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieModels, null, page + 1)
                        loading(true)
                    }, {
                        error(it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        loading(true)
        compositeDisposable.add(
            MainApiClient.getMoviesApi().getMovies(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.movieModels, params.key + 1)
                            loading(false)
                        } else {
                            output.endOfList.postValue(true)
                        }
                    }, {
                        error(it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
    }


    /**
     *
     */
    private fun loading(load: Boolean) {
        output.loading.postValue(load)
    }

    /**
     *
     */
    private fun error(error: String?) {
        error?.let {
            Timber.e("Error: $it")
            output.error.postValue(it)
        }
        loading(false)
    }
}