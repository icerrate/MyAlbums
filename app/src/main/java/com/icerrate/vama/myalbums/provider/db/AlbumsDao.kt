package com.icerrate.vama.myalbums.provider.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.icerrate.vama.myalbums.data.model.Album
import io.reactivex.Single

@Dao
interface AlbumsDao {

    @Insert(onConflict = REPLACE)
    fun saveAlbums(list: List<Album>)

    @Query("DELETE FROM album")
    fun clearAlbums()

    @Query("SELECT * FROM album LIMIT :limit")
    fun getAlbums(limit: Int): Single<List<Album>>
}