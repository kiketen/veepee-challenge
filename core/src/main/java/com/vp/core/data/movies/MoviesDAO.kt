package com.vp.core.data.movies

import androidx.room.*

@Dao
interface MoviesDAO {

    @Transaction
    @Query("UPDATE MoviesDAOModel SET isFavorite=:isFavorite WHERE id = :id")
    fun setFavorite(id: String, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setAll(search: List<MoviesDAOModel>)
}