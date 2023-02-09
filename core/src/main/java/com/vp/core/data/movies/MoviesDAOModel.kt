package com.vp.core.data.movies

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class MoviesDAOModel(
        val id: String,
        val title: String,
        val year: String,
        val poster: String,
        val isFavorite: Boolean
)