package com.icerrate.vama.myalbums.provider.cloud

import com.icerrate.vama.myalbums.data.model.response.AlbumsResponse
import com.icerrate.vama.myalbums.data.model.response.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumApi {

    @GET("us/music/most-played/{limit}/albums.json")
    fun fetchAlbums(@Path("limit") limit: Int): Single<Response<AlbumsResponse>>
}