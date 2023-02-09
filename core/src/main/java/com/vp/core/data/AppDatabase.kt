package com.vp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vp.core.data.movies.FavoritesDAO
import com.vp.core.data.movies.MovieDAOModel

@Database(
        entities = [MovieDAOModel::class],
        version = 1,
        exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDAO(): FavoritesDAO
}