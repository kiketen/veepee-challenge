package com.vp.core.data.movies

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class MovieDAOModel(
        val id: String,
        val title: String,
        val year: String,
        val runtime: String,
        val director: String,
        val plot: String,
        val poster: String
)