package com.icerrate.vama.myalbums.data.source

import com.icerrate.vama.myalbums.data.model.Album
import io.reactivex.Single

interface AlbumDataSource {

    fun getAlbums(limit: Int): Single<List<Album>>
}