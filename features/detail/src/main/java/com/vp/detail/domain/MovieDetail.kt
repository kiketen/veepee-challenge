package com.vp.detail.domain

import com.vp.core.data.movies.MovieDAOModel

data class MovieDetail(
        val id: String,
        val title: String,
        val year: String,
        val runtime: String,
        val director: String,
        val plot: String,
        val poster: String,
        val isFavorite: Boolean
)

fun MovieDetail.toDAO() = MovieDAOModel(
        id = id,
        title = title,
        year = year,
        runtime = runtime,
        director = director,
        plot = plot,
        poster = poster
)