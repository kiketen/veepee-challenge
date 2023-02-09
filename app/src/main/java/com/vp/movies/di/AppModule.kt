package com.vp.movies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vp.core.data.AppDatabase
import com.vp.movies.DataBaseMigrations
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "AppDatabase"
        ).addMigrations(*DataBaseMigrations.getMigrations()).fallbackToDestructiveMigration().build()
    }
}