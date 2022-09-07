package com.icerrate.vama.myalbums.data.model.response

import com.icerrate.vama.myalbums.data.model.Album

data class AlbumsResponse(
    val title: String,
    val country: String,
    val copyright: String,
    val results: List<Album>
)
