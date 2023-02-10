package com.vp.detail.data

import com.google.gson.annotations.SerializedName
import com.vp.detail.domain.MovieDetail

data class MovieDetailApiModel(@SerializedName("Title") val title: String,
                               @SerializedName("Year") val year: String,
                               @SerializedName("Runtime") val runtime: String,
                               @SerializedName("Director") val director: String,
                               @SerializedName("Plot") val plot: String,
                               @SerializedName("Poster") val poster: String)

fun MovieDetailApiModel.toDomain(id: String, isFavorite: Boolean) = MovieDetail(
        id = id,
        title = title,
        year = year,
        runtime = runtime,
        director = director,
        plot = plot,
        poster = poster,
        isFavorite = isFavorite
)