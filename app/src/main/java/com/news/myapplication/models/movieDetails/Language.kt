package com.news.myapplication.models.movieDetails

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("name")
    val name: String
)