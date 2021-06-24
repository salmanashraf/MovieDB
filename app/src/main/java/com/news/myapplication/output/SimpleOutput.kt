package com.news.myapplication.output

class SimpleOutput<S, E> : BaseOutput<E>() {
    val complete by lazy { SingleLiveEvent<S>() }
}