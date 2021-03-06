package com.news.myapplication.output

open class BaseOutput<E> {
    val loading by lazy { SingleLiveEvent<Boolean>() }
    val error by lazy { SingleLiveEvent<E>() }
    val endOfList by lazy { SingleLiveEvent<Boolean>() }
}