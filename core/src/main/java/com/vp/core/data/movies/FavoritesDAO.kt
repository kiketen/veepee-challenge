package com.vp.core.data.movies

import androidx.room.*

@Dao
interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun set(search: MovieDAOModel)

    @Transaction
    @Query("SELECT * FROM MovieDAOModel WHERE id = :id")
    fun get(id: String): MovieDAOModel?
}