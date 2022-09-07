package com.icerrate.vama.myalbums.provider.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.icerrate.vama.myalbums.data.model.Album
import com.icerrate.vama.myalbums.data.model.converter.GenresListConverter

@Database(entities = [Album::class], version = 1, exportSchema = false)
@TypeConverters(GenresListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
}