package com.icerrate.vama.myalbums.data.source

import com.icerrate.vama.myalbums.data.model.Album
import com.icerrate.vama.myalbums.data.model.error.NotAvailableException
import com.icerrate.vama.myalbums.provider.db.AlbumsDao
import com.icerrate.vama.myalbums.provider.db.AppDatabase
import com.icerrate.vama.myalbums.provider.cloud.AlbumApi
import com.icerrate.vama.myalbums.provider.preference.AppPreferences
import io.reactivex.Single
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val api: AlbumApi,
    appDatabase: AppDatabase,
    private val appPreferences: AppPreferences
) : AlbumDataSource {

    private var albumDao: AlbumsDao = appDatabase.albumsDao()

    override fun getAlbums(limit: Int): Single<List<Album>> {
        return api.fetchAlbums(limit)
            .map {
                val feed = it.feed
                appPreferences.saveCopyrightText(feed.copyright)
                feed.results
            }
            .onErrorResumeNext {
                albumDao.getAlbums(limit).flatMap {
                    if (it.isEmpty()) {
                        Single.error(NotAvailableException("Remote & Local sources failed"))
                    } else {
                        Single.just(it)
                    }
                }
            }.doOnSuccess { albums ->
                albumDao.clearAlbums()
                albumDao.saveAlbums(albums)
            }
    }
}