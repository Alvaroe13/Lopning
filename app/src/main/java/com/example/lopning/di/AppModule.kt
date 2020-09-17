package com.example.lopning.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lopning.cache.RunDao
import com.example.lopning.cache.RunDatabase
import com.example.lopning.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //this is the one to use for application life span
object AppModule {

    @Singleton
    @Provides
    fun provideDatabaseInstance( @ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            RunDatabase::class.java,
            DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideDao( db : RunDatabase)  = db.getDao()

}