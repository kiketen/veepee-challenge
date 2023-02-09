package com.vp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vp.core.data.movies.MoviesDAO
import com.vp.core.data.movies.MoviesDAOModel

@Database(
        entities = [MoviesDAOModel::class],
        version = 1,
        exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO
}