package com.icerrate.vama.myalbums.ui.list.usecase

import com.icerrate.vama.myalbums.data.model.Album
import com.icerrate.vama.myalbums.data.source.AlbumRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {

    fun getAlbums(limit: Int = 100): Single<List<Album>> = albumRepository.getAlbums(limit)
}